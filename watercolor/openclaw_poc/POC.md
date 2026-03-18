# 🚀 POC: Lightweight Sandbox Architecture for Multi‑Agent Meeting System  
*A step‑by‑step build plan for an AI coding agent (Node + TypeScript)*

This document defines the exact steps the AI coding agent must follow to create a minimal project with a **sandbox** for rapid prototyping.  
The AI must follow these steps **in order** and **only modify the files described**.

---

# A. Create a new Node + TypeScript project

### Steps
1. Initialize a new Node project  
2. Install TypeScript and ts-node  
3. Create a basic `tsconfig.json`  
4. Create the folder structure:

```
project/
  src/
  test/
  sandbox/
  package.json
  tsconfig.json
```

### Requirements
- ES module or CommonJS is fine  
- Target: ES2020 or later  
- Strict mode ON  

---

# B. Create minimal base classes (the “real system”)

Create these files under `src/`:

```
src/Agent.ts
src/Session.ts
src/Message.ts
src/index.ts
```

### Minimal behavior

#### `Agent`
- `name: string`
- `sessions: Session[]`
- `createSession(type: string): Session`
- No business logic yet

#### `Session`
- `type: string`
- `process(input: string): string`
- Returns a simple string for now

#### `Message`
- `{ from: string; text: string }`

#### `index.ts`
- Export Agent, Session, Message

---

# C. Create a test project

Use any test runner (Jest, Vitest, Mocha).

Create:

```
test/basic.test.ts
```

### Test requirements
- Create an Agent  
- Create a Session  
- Call `process()`  
- Assert it returns a string  

---

# D. Build + test run

The AI must:

- Build the TypeScript project  
- Run the tests  
- Confirm everything passes  

---

# E. Create the sandbox folder

The sandbox is the **lightweight coding surface**.  
The AI coding agent must work **only inside this folder** for all new features.

Create these files:

```
sandbox/
  AgentSandbox.ts
  SessionSandbox.ts
  Meeting.ts
  SessionSelector.ts
  SandboxRunner.ts
  README.md
```

### Rules for sandbox
- Sandbox classes **extend** the real classes in `src/`
- No wrappers  
- No modification to `src/`  
- All new logic lives here  
- AI must not scan or modify `src/` or `test/`  

---

# 🛑 STOP POINT A  
Stop after creating the sandbox folder and empty files.  
Do not implement sandbox logic yet.  
Do not create meeting logic yet.

---

# F. (After STOP) Implement sandbox classes

When instructed to continue, the AI will implement:

### 1. `AgentSandbox`
- Extends `Agent`
- Adds `getMeetingSession()`
- Uses `SessionSelector`

### 2. `SessionSandbox`
- Extends `Session`
- Overrides `process()` for meeting behavior

### 3. `SessionSelector`
- Returns existing meeting session if available  
- Otherwise creates a new one  
- Simple version first

### 4. `Meeting`
- Holds a list of agents  
- Adds agents to meeting  
- Broadcasts greetings  
- Agents respond using their meeting session

### 5. `SandboxRunner`
- Creates several AgentSandbox instances  
- Starts a meeting  
- Prints output to console  

---

# G. Build + test run the sandbox

The AI must:

- Build the project  
- Run `SandboxRunner.ts`  
- Confirm:
  - Agents join the meeting  
  - Agents greet each other  
  - Sessions are reused or created correctly  

---

# 🎯 End of POC

This POC gives you:

- A clean Node + TS project  
- Minimal real classes  
- A fully isolated sandbox  
- Inheritance‑based overrides  
- A place where AI coding agents can work with minimal tokens  
- A safe environment to evolve your multi‑agent meeting concept 


