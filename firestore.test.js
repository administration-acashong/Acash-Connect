const {
  initializeTestEnvironment,
  assertFails,
  assertSucceeds,
} = require("@firebase/rules-unit-testing");
const { test, before, after, beforeEach } = require("node:test");
const fs = require("node:fs");

let testEnv;
const PROJECT_ID = process.env.GCP_PROJECT || "demo-no-project";
const DATABASE_ID = "ai-studio-android-acashcon-767c7371-5f28-428d-8e70-ac9de46d07d1";

before(async () => {
  testEnv = await initializeTestEnvironment({
    projectId: PROJECT_ID,
    firestore: {
      host: "127.0.0.1",
      port: 8085,
      rules: fs.readFileSync("firestore.rules", "utf8"),
    },
  });
});

after(async () => {
  if (testEnv) {
    await testEnv.cleanup();
  }
});

beforeEach(async () => {
  if (testEnv) {
    await testEnv.clearFirestore();
  }
});

test("Authenticated user can create their own valid profile", async () => {
  const aliceContext = testEnv.authenticatedContext("user_alice");
  const db = aliceContext.firestore();

  const validProfile = {
    userId: "user_alice",
    fullName: "Alice Traoré",
    email: "alice@acash.org",
    phone: "+33612345678",
    bloodGroup: "O+",
    restingBpm: 60,
    maxBpm: 192,
    heightCm: 188,
    weightKg: 82,
    gender: "Féminin",
    role: "Athlète",
    positionOrSpecialty: "Shooter 2-pts",
    teamOrClub: "Acash 3x3 Paris",
    bio: "Athlète FIBA 3X3",
    city: "Paris"
  };

  await assertSucceeds(
    db.collection("users").doc("user_alice").set(validProfile)
  );
});

test("User cannot create profile with invalid blood group", async () => {
  const aliceContext = testEnv.authenticatedContext("user_alice");
  const db = aliceContext.firestore();

  const invalidProfile = {
    userId: "user_alice",
    email: "alice@acash.org",
    phone: "+33612345678",
    bloodGroup: "Z+", // Invalid
    restingBpm: 60,
    maxBpm: 192,
    heightCm: 188,
    weightKg: 82,
    gender: "Féminin"
  };

  await assertFails(
    db.collection("users").doc("user_alice").set(invalidProfile)
  );
});

test("User cannot read or write another user's profile", async () => {
  const aliceContext = testEnv.authenticatedContext("user_alice");
  const bobContext = testEnv.authenticatedContext("user_bob");
  const dbAlice = aliceContext.firestore();
  const dbBob = bobContext.firestore();

  await dbAlice.collection("users").doc("user_alice").set({
    userId: "user_alice",
    email: "alice@acash.org",
    phone: "+33612345678",
    bloodGroup: "O+",
    restingBpm: 60,
    maxBpm: 192,
    heightCm: 188,
    weightKg: 82,
    gender: "Féminin"
  });

  // Bob tries to read Alice's profile -> should fail
  await assertFails(dbBob.collection("users").doc("user_alice").get());

  // Bob tries to write to Alice's profile -> should fail
  await assertFails(
    dbBob.collection("users").doc("user_alice").set({
      userId: "user_alice",
      email: "hacked@evil.org",
      phone: "000",
      bloodGroup: "A+",
      restingBpm: 70,
      maxBpm: 180,
      heightCm: 180,
      weightKg: 80,
      gender: "Masculin"
    })
  );
});

test("Unauthenticated user cannot read or write profiles", async () => {
  const unauthContext = testEnv.unauthenticatedContext();
  const db = unauthContext.firestore();

  await assertFails(db.collection("users").doc("user_any").get());
  await assertFails(
    db.collection("users").doc("user_any").set({
      userId: "user_any",
      email: "anon@test.com",
      phone: "123",
      bloodGroup: "A+",
      restingBpm: 60,
      maxBpm: 180,
      heightCm: 180,
      weightKg: 80,
      gender: "Masculin"
    })
  );
});
