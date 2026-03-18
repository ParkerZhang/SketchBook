# 🌱 Stage 3 — Move Demo Flow Into Test Case & Prepare for CLI Commands  
All changes occur **only inside `/sandbox`** and **inside `/test`**.  
Do **not** modify `/src`.

The purpose of Stage 3 is to:

- Extract the demo logic from `SandboxRunner.ts`
- Turn it into a **repeatable, deterministic test case**
- Prepare the sandbox for a future CLI interface
- Leave `SandboxRunner.ts` empty or minimal for now

This stage does **not** implement the CLI yet — only prepares for it.

---

## A. Create a New Test File for the Demo Flow

Create:

```
test/stage3.demoFlow.test.ts
```

This test must reproduce the entire Stage 2 demo scenario.

### Required test flow

1. Create a `MeetingEngine`
2. Send a `start` command for subject `"Project Review"`
3. Add three agents:
   - Alice  
   - Bob  
   - Carol  
4. Verify:
   - join logs  
   - greeting logs  
   - cross‑greetings  
5. Send a `note` command  
6. Send a `stop` command  
7. Send a `resume` command  
8. Verify:
   - resume logs  
   - “is back!” messages  
   - resumed greetings  
9. Send an `end` command  
10. Verify:
   - summary text  
   - meeting removed from engine  

### Required assertions

```ts
expect(meeting.log.length).toBeGreaterThan(0);
expect(meeting.log.some(l => l.includes("joins the meeting"))).toBe(true);
expect(meeting.log.some(l => l.includes("Nice to meet you"))).toBe(true);
expect(meeting.notes.length).toBe(1);
expect(engine.getMeeting("Project Review")).toBeUndefined();
```

This test replaces the manual demo in `SandboxRunner.ts`.

---

## B. Update SandboxRunner

Modify:

```
sandbox/SandboxRunner.ts
```

Replace all demo logic with:

```ts
console.log("SandboxRunner is now reserved for CLI mode (Stage 4).");
```

Do **not** run any meeting logic here.

This file will become the CLI entry point in Stage 4.

---

## C. Prepare for CLI (Do Not Implement Yet)

Create a placeholder file:

```
sandbox/CLI.ts
```

Add only:

```ts
export class CLI {
  // Stage 4 will implement:
  // - stdin listener
  // - command parser
  // - command dispatcher
}
```

No logic yet.

---

## D. Update README

Modify:

```
sandbox/README.md
```

Add a new section:

### Stage 3 — Demo Flow Moved to Tests

- The demo scenario previously in `SandboxRunner.ts` is now a repeatable test.
- `SandboxRunner.ts` is reserved for CLI mode in Stage 4.
- A new test file `stage3.demoFlow.test.ts` validates the full meeting lifecycle.

---

## 🛑 STOP POINT E

Stop after:

- Creating `stage3.demoFlow.test.ts`
- Moving the demo logic into the test
- Clearing `SandboxRunner.ts` to a placeholder
- Adding `CLI.ts` as an empty shell
- Updating README

Do **not**:

- Implement CLI parsing  
- Implement stdin listeners  
- Modify meeting logic  
- Modify earlier tests  
- Modify `/src`  

This completes Stage 3.

