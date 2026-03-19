# 🌱 Stage 4 — External Command Function + CLI Adapter  
All changes occur **only inside `/sandbox`** and **inside `/test`**.  
Do **not** modify `/src`.

The purpose of Stage 4 is to:

- Add a **universal external command function** to `MeetingEngine`
- Allow production systems to call this function directly
- Implement a **thin CLI adapter** that forwards input to this function
- Add tests for the parser and external command handler
- Keep the CLI dumb and the engine smart

This stage does **not** implement advanced CLI features (history, colors, etc.).

---

# A. Add a Universal External Command Function

Modify:

```
sandbox/MeetingEngine.ts
```

Add a new public method:

```ts
public handleExternalCommand(input: string): void {
  // 1. Parse input into a Command object (use CLI.parse)
  // 2. Push command into queue
  // 3. Tick the engine
}
```

This becomes the **single entry point** for:

- CLI  
- Production code  
- Tests  
- Automation scripts  

---

# B. Implement a CLI Parser

Modify:

```
sandbox/CLI.ts
```

Add:

```ts
export class CLI {
  constructor(private engine: MeetingEngine) {}

  // Parse a raw string into a Command object
  parse(input: string): Command {
    // Required syntax:
    // start "Subject"
    // agent "Subject" Name
    // note "Subject" "Text"
    // stop "Subject"
    // resume "Subject"
    // end "Subject"
  }

  // Forward parsed command to engine
  handle(input: string): void {
    const cmd = this.parse(input);
    this.engine.handleExternalCommand(input);
  }

  // Optional: stdin loop (not required for tests)
  start(): void {
    // Stage 4: implement a simple stdin listener
  }
}
```

### Required parsing behavior

- Quoted strings must be supported  
- Commands must map to the existing `Command` type  
- Invalid commands must throw an error  

---

# C. Update SandboxRunner to Use CLI

Modify:

```
sandbox/SandboxRunner.ts
```

Replace placeholder with:

```ts
import { MeetingEngine } from './MeetingEngine';
import { CLI } from './CLI';

const engine = new MeetingEngine();
const cli = new CLI(engine);

console.log("CLI mode active. Type commands:");

cli.start(); // stdin loop
```

This makes SandboxRunner the **CLI entry point**.

---

# D. Add Tests for CLI Parsing and External Command Handling

Create:

```
test/stage4.cli.test.ts
```

### Required tests

#### 1. Parsing commands

```ts
expect(cli.parse('start "ABC"')).toEqual({ type: "start", subject: "ABC" });
expect(cli.parse('stop "ABC"')).toEqual({ type: "stop", subject: "ABC" });
expect(cli.parse('resume "ABC"')).toEqual({ type: "resume", subject: "ABC" });
expect(cli.parse('note "ABC" "Hello"')).toEqual({
  type: "note",
  subject: "ABC",
  text: "Hello"
});
expect(cli.parse('end "ABC"')).toEqual({ type: "end", subject: "ABC" });
```

#### 2. External command handler

```ts
engine.handleExternalCommand('start "Demo"');
const meeting = engine.getMeeting("Demo");
expect(meeting).toBeDefined();
```

#### 3. Full flow via external commands

```ts
engine.handleExternalCommand('start "Flow"');
engine.handleExternalCommand('note "Flow" "Testing"');
engine.handleExternalCommand('end "Flow"');

expect(engine.getMeeting("Flow")).toBeUndefined();
```

---

# E. Update README

Modify:

```
sandbox/README.md
```

Add:

### Stage 4 — External Command Function + CLI

- `MeetingEngine.handleExternalCommand()` is now the universal entry point.
- Production systems, tests, and CLI all call this function.
- `CLI.parse()` converts raw text into a `Command`.
- `SandboxRunner.ts` now launches CLI mode.

---

# F. Demo Script

Create a demo that showcases the full system with agents, greetings, discussion, and meeting lifecycle.

## `sandbox/Demo.ts`

```typescript
import { MeetingEngine } from './MeetingEngine';
import { AgentSandbox } from './AgentSandbox';

export class Demo {
  static run(): void {
    console.log('═══════════════════════════════════════════════');
    console.log('          STAGE 4 DEMO - Meeting System        ');
    console.log('═══════════════════════════════════════════════\n');

    this.runShortMeeting();
    console.log('\n');
    this.runLongMeeting();
  }

  static runShortMeeting(): void {
    console.log('┌─────────────────────────────────────────────┐');
    console.log('│  DEMO 1: Short Meeting                      │');
    console.log('└─────────────────────────────────────────────┘\n');

    const engine = new MeetingEngine();
    engine.handleExternalCommand('start "Quick Sync"');
    const meeting = engine.getMeeting('Quick Sync')!;

    const alice = new AgentSandbox('Alice');
    const bob = new AgentSandbox('Bob');
    meeting.addAgent(alice);
    meeting.addAgent(bob);

    console.log('\n--- Meeting Log ---');
    console.log(meeting.log.join('\n'));

    console.log('\n--- Discussion ---');
    meeting.discuss(alice, 'The deployment is ready!');
    meeting.discuss(bob, 'Great, I\'ll review the PR.');
    console.log(meeting.log.slice(meeting.log.indexOf('Alice: Hi The deployment')).join('\n'));

    engine.handleExternalCommand('note "Quick Sync" "Deployment approved"');
    engine.handleExternalCommand('end "Quick Sync"');
  }

  static runLongMeeting(): void {
    console.log('┌─────────────────────────────────────────────┐');
    console.log('│  DEMO 2: Long Meeting (stopped & resumed)   │');
    console.log('└─────────────────────────────────────────────┘\n');

    const engine = new MeetingEngine();
    engine.handleExternalCommand('start "Sprint Planning"');
    const meeting = engine.getMeeting('Sprint Planning')!;

    console.log('--- Agents Joining ---');
    const alice = new AgentSandbox('Alice');
    const bob = new AgentSandbox('Bob');
    const carol = new AgentSandbox('Carol');
    const dave = new AgentSandbox('Dave');
    meeting.addAgent(alice);
    meeting.addAgent(bob);
    meeting.addAgent(carol);
    meeting.addAgent(dave);
    console.log(meeting.log.join('\n'));

    console.log('\n--- Discussion Session 1 ---');
    meeting.discuss(carol, 'Let\'s prioritize the new API feature.');
    meeting.discuss(alice, 'I agree, but we also need to fix the login bug.');
    meeting.discuss(bob, 'The bug affects 20% of users. Should be critical.');

    const idx = meeting.log.length;
    engine.handleExternalCommand('note "Sprint Planning" "API feature is P1"');
    engine.handleExternalCommand('note "Sprint Planning" "Login bug is critical"');
    console.log(meeting.log.slice(idx).join('\n'));

    console.log('\n--- Meeting Paused (Lunch Break) ---\n');
    engine.handleExternalCommand('stop "Sprint Planning"');

    console.log('--- Meeting Resumed (After Lunch) ---\n');
    engine.handleExternalCommand('resume "Sprint Planning"');
    console.log(meeting.log.slice(idx).join('\n'));

    console.log('\n--- Discussion Session 2 ---');
    const idx2 = meeting.log.length;
    meeting.discuss(dave, 'I\'ll take the login bug fix.');
    meeting.discuss(alice, 'Thanks Dave! I\'ll handle the API design.');
    meeting.discuss(carol, 'Bob and I will work on the backend implementation.');
    console.log(meeting.log.slice(idx2).join('\n'));

    engine.handleExternalCommand('note "Sprint Planning" "Dave: login bug"');
    engine.handleExternalCommand('note "Sprint Planning" "Alice: API design"');
    engine.handleExternalCommand('note "Sprint Planning" "Bob+Carol: backend"');
    engine.handleExternalCommand('end "Sprint Planning"');
  }
}

Demo.run();
```

---

## Update `SandboxRunner.ts`

```typescript
import { Demo } from './Demo';

console.log('Running Stage 4 Demo...\n');
Demo.run();
```

---

## Expected Output

```
═══════════════════════════════════════════════
          STAGE 4 DEMO - Meeting System        
═══════════════════════════════════════════════

┌─────────────────────────────────────────────┐
│  DEMO 1: Short Meeting                      │
└─────────────────────────────────────────────┘

Meeting 'Quick Sync' started.

--- Meeting Log ---
Alice joins the meeting.
Alice: Hi Hello everyone! Nice to meet you! I'm Alice.
Bob joins the meeting.
Bob: Hi Hello everyone! Nice to meet you! I'm Bob.
Alice: Hi Bob Nice to meet you! I'm Alice.

--- Discussion ---
Bob: Hi Great, I'll review the PR. Nice to meet you! I'm Bob.
Note: Deployment approved
Meeting: Quick Sync
Agents: 2
Notes: 1
Meeting 'Quick Sync' ended.


┌─────────────────────────────────────────────┐
│  DEMO 2: Long Meeting (stopped & resumed)   │
└─────────────────────────────────────────────┘

Meeting 'Sprint Planning' started.
--- Agents Joining ---
Alice joins the meeting.
Alice: Hi Hello everyone! Nice to meet you! I'm Alice.
Bob joins the meeting.
Bob: Hi Hello everyone! Nice to meet you! I'm Bob.
Alice: Hi Bob Nice to meet you! I'm Alice.
Carol joins the meeting.
Carol: Hi Hello everyone! Nice to meet you! I'm Carol.
Alice: Hi Carol Nice to meet you! I'm Alice.
Bob: Hi Carol Nice to meet you! I'm Bob.
Dave joins the meeting.
Dave: Hi Hello everyone! Nice to meet you! I'm Dave.
Alice: Hi Dave Nice to meet you! I'm Alice.
Bob: Hi Dave Nice to meet you! I'm Bob.
Carol: Hi Dave Nice to meet you! I'm Carol.

--- Discussion Session 1 ---
Note: API feature is P1
Note: Login bug is critical


--- Meeting Paused (Lunch Break) ---

Meeting 'Sprint Planning' paused.
--- Meeting Resumed (After Lunch) ---

Meeting 'Sprint Planning' resumed.
--- Meeting Resumed ---
Alice is back!
Bob is back!
Carol is back!
Dave is back!
Alice greets Bob: Hi Bob Nice to meet you! I'm Alice.
Alice greets Carol: Hi Carol Nice to meet you! I'm Alice.
Alice greets Dave: Hi Dave Nice to meet you! I'm Alice.
Bob greets Alice: Hi Alice Nice to meet you! I'm Bob.
Bob greets Carol: Hi Carol Nice to meet you! I'm Bob.
Bob greets Dave: Hi Dave Nice to meet you! I'm Bob.
Carol greets Alice: Hi Alice Nice to meet you! I'm Carol.
Carol greets Bob: Hi Bob Nice to meet you! I'm Carol.
Carol greets Dave: Hi Dave Nice to meet you! I'm Carol.
Dave greets Alice: Hi Alice Nice to meet you! I'm Dave.
Dave greets Bob: Hi Bob Nice to meet you! I'm Dave.
Dave greets Carol: Hi Carol Nice to meet you! I'm Dave.

--- Discussion Session 2 ---
Dave: Hi I'll take the login bug fix. Nice to meet you! I'm Dave.
Alice: Hi Thanks Dave! I'll handle the API design. Nice to meet you! I'm Alice.
Carol: Hi Bob and I will work on the backend implementation. Nice to meet you! I'm Carol.
Note: Dave: login bug
Note: Alice: API design
Note: Bob+Carol: backend
Meeting: Sprint Planning
Agents: 4
Notes: 5
Meeting 'Sprint Planning' ended.
```

---

## 🛑 STOP POINT G

Stop after:

- Adding `handleExternalCommand()`  
- Implementing CLI parser  
- Updating SandboxRunner to call CLI  
- Adding `stage4.cli.test.ts`  
- Adding `Demo.ts` with full demo script
- Updating README  

Do **not**:

- Add advanced CLI features  
- Add agent conversation logic  
- Modify earlier tests  
- Modify `/src`  

This completes Stage 4.

---

# 📋 TODO: Stage 5 — Production Integration

Plug meeting into real OpenClaw production classes.

## A. Rename Production Agent

- [ ] Rename `Agent` → `OrgAgent` in `src/Agent.ts`
- [ ] Update all imports in `src/index.ts`
- [ ] Update `AgentSandbox` to extend `OrgAgent`

## B. Create Org Class

- [ ] Create `src/Org.ts`:
  ```typescript
  export class Org {
    orgId: string;
    name: string;
    meetings: Meeting[];
    agents: OrgAgent[];

    constructor(orgId: string, name: string);
    addAgent(agent: OrgAgent): void;
    getMeeting(subject: string): Meeting | undefined;
    getAllMeetings(): Meeting[];
  }
  ```

## C. Update Meeting Class

- [ ] Add `org: Org` field to `Meeting`
- [ ] Meeting belongs to one Org
- [ ] Meeting constructor accepts Org parameter

## D. Plug Into Gateway

- [ ] Gateway loads/creates Org on startup
- [ ] OrgAgent spawns within Org context
- [ ] Gateway exposes meeting functions:
  - `startMeeting(subject)`
  - `stopMeeting(subject)`
  - `resumeMeeting(subject)`
  - `endMeeting(subject)`
  - `addNote(subject, text)`

## E. Meeting Resume in Gateway

- [ ] `Org.resumeMeetings()` - called by gateway on startup
- [ ] Resumes all paused meetings
- [ ] Triggers `resumeGreetings()` for each meeting

## F. File Structure (Updated)

```
src/                          # Production classes
├── Agent.ts                  # RENAMED: OrgAgent
├── Org.ts                    # NEW: Org container
├── Session.ts
├── Message.ts
└── index.ts

sandbox/                      # Sandbox extensions
├── AgentSandbox.ts           # Extends OrgAgent
├── OrgSandbox.ts             # NEW: extends Org
├── Meeting.ts                # Updated: has org
├── MeetingEngine.ts
└── ...
```

## G. Key Relationships

```
Gateway
  └── Org (loaded from config)
        ├── OrgAgent[] (agents in org)
        └── Meeting[] (meetings in org)
              └── agents discuss together
```


