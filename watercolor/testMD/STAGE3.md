# 🌱 Stage 3 — Move Demo Flow Into Test Case & Prepare for CLI Commands

All changes occur **only inside `/sandbox`** and **inside `/test`**. Do **not** modify `/src`.

---

## A. Add discuss() to Meeting

### `sandbox/Meeting.ts`

Add this method to the existing Meeting class:

```typescript
discuss(agent: AgentSandbox, message: string): void {
  const session = this.selector.selectSession(agent, 'meeting') as SessionSandbox;
  session.setAgentName(agent.name);
  const response = session.process(message);
  this.log.push(`${agent.name}: ${response}`);
}
```

---

## B. Create Demo Flow Test

### `test/stage3.demoFlow.test.ts`

```typescript
import { MeetingEngine } from '../sandbox/MeetingEngine';
import { AgentSandbox } from '../sandbox/AgentSandbox';

describe('Stage 3 - Demo Flow Test', () => {
  test('Full meeting lifecycle with all commands', () => {
    const engine = new MeetingEngine();

    // START meeting
    engine.sendCommand({ type: 'start', subject: 'Project Review' });
    engine.tick();

    const meeting = engine.getMeeting('Project Review')!;
    expect(meeting.subject).toBe('Project Review');
    expect(meeting.active).toBe(true);

    // Add agents - each agent joins and greets
    const alice = new AgentSandbox('Alice');
    const bob = new AgentSandbox('Bob');
    const carol = new AgentSandbox('Carol');

    meeting.addAgent(alice);
    meeting.addAgent(bob);
    meeting.addAgent(carol);

    expect(meeting.getAgentCount()).toBe(3);
    expect(meeting.log.filter(l => l.includes('joins the meeting')).length).toBe(3);
    expect(meeting.log.filter(l => l.includes('Hello everyone!')).length).toBe(3);

    // Initial discussion
    meeting.discuss(alice, 'Let\'s discuss Q1 goals.');
    meeting.discuss(bob, 'I think we should focus on feature X.');
    meeting.discuss(carol, 'Agreed, plus we need to address tech debt.');

    console.log('\n=== Meeting Log After Joins ===');
    console.log(meeting.log.join('\n'));

    // Add note
    engine.sendCommand({ type: 'note', subject: 'Project Review', text: 'Discuss Q1 goals' });
    engine.tick();
    expect(meeting.notes.length).toBe(1);

    // Pause meeting
    engine.sendCommand({ type: 'stop', subject: 'Project Review' });
    engine.tick();
    expect(meeting.active).toBe(false);

    // Resume meeting
    engine.sendCommand({ type: 'resume', subject: 'Project Review' });
    engine.tick();
    expect(meeting.active).toBe(true);

    expect(meeting.log.some(l => l.includes('Meeting Resumed'))).toBe(true);
    expect(meeting.log.filter(l => l.includes('is back!')).length).toBe(3);

    // Discussion after resume
    meeting.discuss(bob, 'Where were we?');
    meeting.discuss(alice, 'We were discussing Q1 priorities.');
    meeting.discuss(carol, 'Right, feature X and tech debt.');

    console.log('\n=== Final Meeting Log ===');
    console.log(meeting.log.join('\n'));

    // Verify discussions happened
    expect(meeting.log.some(l => l.includes('discuss Q1 goals'))).toBe(true);
    expect(meeting.log.some(l => l.includes('feature X'))).toBe(true);
    expect(meeting.log.some(l => l.includes('tech debt'))).toBe(true);
    expect(meeting.log.some(l => l.includes('Where were we'))).toBe(true);

    // End meeting
    engine.sendCommand({ type: 'end', subject: 'Project Review' });
    engine.tick();

    expect(engine.getMeeting('Project Review')).toBeUndefined();
  });
});
```

---

## C. Update SandboxRunner

### `sandbox/SandboxRunner.ts`

```typescript
console.log("SandboxRunner is now reserved for CLI mode (Stage 4).");
```

---

## D. Create CLI Placeholder

### `sandbox/CLI.ts`

```typescript
export class CLI {
  // Stage 4 will implement:
  // - stdin listener
  // - command parser
  // - command dispatcher
}
```

---

## E. Update README

### `sandbox/README.md`

```markdown
# Sandbox

## Purpose

The sandbox contains experimental code that extends the base classes from `src/`.

## MeetingEngine

The MeetingEngine manages multiple meetings and processes commands.

### Command Types
- `start` - Create a new meeting
- `stop` - Pause a meeting
- `resume` - Resume a paused meeting
- `note` - Add a note to the meeting
- `end` - End and summarize the meeting

### Running

```bash
npm run run:sandbox
```

### Greeting Behavior

**When Joining:** Agent greets everyone, existing agents greet the newcomer.

**Resume:** Agents announce they're back and greet each other.

### Discussion

Agents can discuss topics using `meeting.discuss(agent, message)`.

## Rules
- Sandbox classes extend real classes in `src/`
- No modification to `src/` or `test/`
- All new logic lives in `sandbox/`

---

## Stage 3 — Demo Flow Moved to Tests

- The demo scenario previously in `SandboxRunner.ts` is now a repeatable test.
- `SandboxRunner.ts` is reserved for CLI mode in Stage 4.
- A new test file `stage3.demoFlow.test.ts` validates the full meeting lifecycle.
- Discussion functionality added with `meeting.discuss()`.
```

---

## Expected Output

```
=== Meeting Log After Joins ===

Alice joins the meeting.
Alice: Hi Hello everyone! Nice to meet you! I'm Alice.
Bob joins the meeting.
Bob: Hi Hello everyone! Nice to meet you! I'm Bob.
Alice: Hi Bob Nice to meet you! I'm Alice.
Carol joins the meeting.
Carol: Hi Hello everyone! Nice to meet you! I'm Carol.
Alice: Hi Carol Nice to meet you! I'm Alice.
Bob: Hi Carol Nice to meet you! I'm Bob.
Alice: Hi Let's discuss Q1 goals. Nice to meet you! I'm Alice.
Bob: Hi I think we should focus on feature X. Nice to meet you! I'm Bob.
Carol: Hi Agreed, plus we need to address tech debt. Nice to meet you! I'm Carol.

--- Meeting Resumed ---
Alice is back!
Bob is back!
Carol is back!
Alice greets Bob: Hi Bob Nice to meet you! I'm Alice.
Alice greets Carol: Hi Carol Nice to meet you! I'm Alice.
Bob greets Alice: Hi Alice Nice to meet you! I'm Bob.
Bob greets Carol: Hi Carol Nice to meet you! I'm Bob.
Carol greets Alice: Hi Alice Nice to meet you! I'm Carol.
Carol greets Bob: Hi Bob Nice to meet you! I'm Carol.
Bob: Hi Where were we? Nice to meet you! I'm Bob.
Alice: Hi We were discussing Q1 priorities. Nice to meet you! I'm Alice.
Carol: Hi Right, feature X and tech debt. Nice to meet you! I'm Carol.
```

---

## 🛑 STOP POINT E

Stop after:
- Adding `discuss()` method to Meeting
- Creating `stage3.demoFlow.test.ts` with full demo
- Clearing `SandboxRunner.ts` to placeholder
- Adding `CLI.ts` placeholder
- Updating README

This completes Stage 3.
