# Stage 2 — MeetingEngine, Command-Driven Architecture & Greetings

All changes occur **only inside `/sandbox`** and **inside `/test`**. Do **not** modify `/src`.

---

## A. Command Model

### `sandbox/Command.ts`
```typescript
export type Command =
  | { type: "start"; subject: string }
  | { type: "stop"; subject: string }
  | { type: "resume"; subject: string }
  | { type: "note"; subject: string; text: string }
  | { type: "end"; subject: string };
```

---

## B. Update Meeting Class

### `sandbox/Meeting.ts`
```typescript
import { AgentSandbox } from './AgentSandbox';
import { SessionSelector } from './SessionSelector';
import { SessionSandbox } from './SessionSandbox';

export class Meeting {
  subject: string;
  active: boolean;
  notes: string[];
  agents: AgentSandbox[];
  log: string[];
  private selector: SessionSelector = new SessionSelector();

  constructor(subject: string) {
    this.subject = subject;
    this.active = false;
    this.notes = [];
    this.agents = [];
    this.log = [];
  }

  tick(): void {
    // Empty for now
  }

  addNote(text: string): void {
    this.notes.push(text);
  }

  summarize(): string {
    return `Meeting: ${this.subject}\nAgents: ${this.agents.length}\nNotes: ${this.notes.length}`;
  }

  addAgent(agent: AgentSandbox): void {
    const session = this.selector.selectSession(agent, 'meeting') as SessionSandbox;
    session.setAgentName(agent.name);
    
    this.log.push(`${agent.name} joins the meeting.`);
    
    const greeting = session.process('Hello everyone!');
    this.log.push(`${agent.name}: ${greeting}`);
    
    for (const existingAgent of this.agents) {
      const existingSession = this.selector.selectSession(existingAgent, 'meeting') as SessionSandbox;
      const response = existingSession.process(agent.name);
      this.log.push(`${existingAgent.name}: ${response}`);
    }
    
    this.agents.push(agent);
  }

  resumeGreetings(): void {
    this.log.push('--- Meeting Resumed ---');
    for (const agent of this.agents) {
      const session = this.selector.selectSession(agent, 'meeting') as SessionSandbox;
      this.log.push(`${agent.name} is back!`);
    }
    for (const agent of this.agents) {
      const session = this.selector.selectSession(agent, 'meeting') as SessionSandbox;
      for (const other of this.agents) {
        if (other !== agent) {
          const response = session.process(other.name);
          this.log.push(`${agent.name} greets ${other.name}: ${response}`);
        }
      }
    }
  }

  getAgentCount(): number {
    return this.agents.length;
  }
}
```

---

## C. MeetingEngine

### `sandbox/MeetingEngine.ts`
```typescript
import { Meeting } from './Meeting';
import { Command } from './Command';

export class MeetingEngine {
  private meetings: Map<string, Meeting> = new Map();
  private commandQueue: Command[] = [];

  sendCommand(command: Command): void {
    this.commandQueue.push(command);
  }

  tick(): void {
    while (this.commandQueue.length > 0) {
      const command = this.commandQueue.shift()!;
      this.dispatch(command);
    }
  }

  private dispatch(command: Command): void {
    const meeting = this.meetings.get(command.subject);

    switch (command.type) {
      case 'start':
        const newMeeting = new Meeting(command.subject);
        newMeeting.active = true;
        this.meetings.set(command.subject, newMeeting);
        console.log(`Meeting '${command.subject}' started.`);
        break;
      case 'stop':
        if (meeting) {
          meeting.active = false;
          console.log(`Meeting '${command.subject}' paused.`);
        }
        break;
      case 'resume':
        if (meeting) {
          meeting.active = true;
          meeting.resumeGreetings();
          console.log(`Meeting '${command.subject}' resumed.`);
        }
        break;
      case 'note':
        if (meeting) {
          meeting.addNote(command.text);
          console.log(`Note: ${command.text}`);
        }
        break;
      case 'end':
        if (meeting) {
          console.log(meeting.summarize());
          this.meetings.delete(command.subject);
          console.log(`Meeting '${command.subject}' ended.`);
        }
        break;
    }
  }

  getMeeting(subject: string): Meeting | undefined {
    return this.meetings.get(subject);
  }
}
```

---

## D. Update SandboxRunner

### `sandbox/SandboxRunner.ts`
```typescript
import { AgentSandbox } from './AgentSandbox';
import { MeetingEngine } from './MeetingEngine';

const engine = new MeetingEngine();

// START meeting
engine.sendCommand({ type: 'start', subject: 'Project Review' });
engine.tick();

const meeting = engine.getMeeting('Project Review')!;

// Add agents
const alice = new AgentSandbox('Alice');
const bob = new AgentSandbox('Bob');
const carol = new AgentSandbox('Carol');

meeting.addAgent(alice);
meeting.addAgent(bob);
meeting.addAgent(carol);

console.log('\n--- Meeting Log ---\n');
console.log(meeting.log.join('\n'));

// Add notes
engine.sendCommand({ type: 'note', subject: 'Project Review', text: 'Discuss Q1 goals' });
engine.tick();

console.log('\n--- Pausing Meeting ---\n');
engine.sendCommand({ type: 'stop', subject: 'Project Review' });
engine.tick();

console.log('\n--- Resuming Meeting ---\n');
engine.sendCommand({ type: 'resume', subject: 'Project Review' });
engine.tick();

console.log('\n--- Final Log ---\n');
console.log(meeting.log.join('\n'));

console.log('\n--- Ending Meeting ---\n');
engine.sendCommand({ type: 'end', subject: 'Project Review' });
engine.tick();
```

---

## E. Update Tests

### `test/stage2.greetings.test.ts`
```typescript
import { Meeting } from '../sandbox/Meeting';
import { AgentSandbox } from '../sandbox/AgentSandbox';

describe('Stage 2 - Greetings Tests', () => {
  test('Agent joins are logged', () => {
    const meeting = new Meeting('Test Meeting');
    const agent = new AgentSandbox('Alice');
    meeting.addAgent(agent);
    
    expect(meeting.log[0]).toContain('Alice joins the meeting.');
  });

  test('Agents respond when joining', () => {
    const meeting = new Meeting('Test Meeting');
    const agent = new AgentSandbox('Bob');
    meeting.addAgent(agent);
    
    expect(meeting.log.some(l => l.includes('Bob:'))).toBe(true);
    expect(meeting.log.some(l => l.includes('Nice to meet you!'))).toBe(true);
  });

  test('Existing agents greet newcomer', () => {
    const meeting = new Meeting('Test Meeting');
    const alice = new AgentSandbox('Alice');
    const bob = new AgentSandbox('Bob');
    
    meeting.addAgent(alice);
    meeting.addAgent(bob);
    
    expect(meeting.log.some(l => l.includes('Alice:') && l.includes('Bob'))).toBe(true);
  });

  test('resumeGreetings() logs resuming', () => {
    const meeting = new Meeting('Test Meeting');
    const agent = new AgentSandbox('Alice');
    meeting.addAgent(agent);
    
    meeting.resumeGreetings();
    
    expect(meeting.log.some(l => l.includes('Meeting Resumed'))).toBe(true);
    expect(meeting.log.some(l => l.includes('is back!'))).toBe(true);
  });

  test('Full meeting lifecycle', () => {
    const meeting = new Meeting('Lifecycle Test');
    
    const alice = new AgentSandbox('Alice');
    const bob = new AgentSandbox('Bob');
    
    meeting.addAgent(alice);
    meeting.addAgent(bob);
    
    expect(meeting.getAgentCount()).toBe(2);
    expect(meeting.log.length).toBeGreaterThan(0);
  });
});
```

---

## F. Update README

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

## Rules
- Sandbox classes extend real classes in `src/`
- No modification to `src/` or `test/`
- All new logic lives in `sandbox/`
```

---

## Expected Output

```
Meeting 'Project Review' started.

--- Meeting Log ---

Alice joins the meeting.
Alice: Hi Hello everyone! Nice to meet you! I'm Alice.
Bob joins the meeting.
Bob: Hi Hello everyone! Nice to meet you! I'm Bob.
Alice: Hi Bob Nice to meet you! I'm Alice.
Carol joins the meeting.
Carol: Hi Hello everyone! Nice to meet you! I'm Carol.
Alice: Hi Carol Nice to meet you! I'm Alice.
Bob: Hi Carol Nice to meet you! I'm Bob.

Note: Discuss Q1 goals

--- Pausing Meeting ---

Meeting 'Project Review' paused.

--- Resuming Meeting ---

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

Meeting 'Project Review' resumed.

--- Final Log ---

Alice joins the meeting.
Alice: Hi Hello everyone! Nice to meet you! I'm Alice.
Bob joins the meeting.
Bob: Hi Hello everyone! Nice to meet you! I'm Bob.
Alice: Hi Bob Nice to meet you! I'm Alice.
Carol joins the meeting.
Carol: Hi Hello everyone! Nice to meet you! I'm Carol.
Alice: Hi Carol Nice to meet you! I'm Alice.
Bob: Hi Carol Nice to meet you! I'm Bob.
--- Meeting Resumed ---
Alice is back!
Bob is back!
Carol is back!
Alice greets Bob: Hi Bob Nice to meet you! I'm Alice.
Alice greets Carol: Hi Carol Nice to meet you! I'm Alice.
Bob greets Alice: Hi Alice Nice to meet you! I'm Bob.
Bob greets Carol: Hi Carol Nice to meet you! I'm Bob.
Carol greets Alice: Hi Alice Nice to meet you! I'm Carol.
Carol greets Bob: Hi Bob Nice to meet you! I'm Bob.

--- Ending Meeting ---

Meeting: Project Review
Agents: 3
Notes: 1
Meeting 'Project Review' ended.
```

---

## Next: Stage 3

Externalize the meeting command interface so commands can be sent from:
- command line
- external scripts
- other processes

Stage 3 will introduce:
- CLI command dispatcher
- stdin listener
- command parser
- multi-meeting controller
