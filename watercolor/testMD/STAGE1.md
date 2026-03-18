# POC: Lightweight Sandbox Architecture for Multi-Agent Meeting System

A step-by-step build plan for an AI coding agent (Node + TypeScript).

The AI must follow these steps **in order** and **only modify the files described**.

---

## A. Create a new Node + TypeScript project

### Steps
1. Initialize a new Node project: `npm init -y`
2. Install dependencies: `npm install typescript ts-node @types/node --save-dev`
3. Install Jest for testing: `npm install jest ts-jest @types/jest --save-dev`
4. Create folder structure: `src/`, `test/`, `sandbox/`

### Files to create

**package.json** - add scripts:
```json
{
  "scripts": {
    "build": "tsc",
    "test": "jest",
    "run:sandbox": "ts-node sandbox/SandboxRunner.ts"
  }
}
```

**tsconfig.json**:
```json
{
  "compilerOptions": {
    "target": "ES2020",
    "module": "commonjs",
    "outDir": "./dist",
    "rootDir": "./",
    "strict": true,
    "esModuleInterop": true,
    "skipLibCheck": true,
    "forceConsistentCasingInFileNames": true
  },
  "include": ["src/**/*", "test/**/*", "sandbox/**/*"],
  "exclude": ["node_modules", "dist"]
}
```

**jest.config.js**:
```js
module.exports = {
  preset: 'ts-jest',
  testEnvironment: 'node',
  roots: ['<rootDir>/test'],
  testMatch: ['**/*.test.ts']
};
```

---

## B. Create minimal base classes

Create these files under `src/`:

### `src/Agent.ts`
```typescript
import { Session } from './Session';

export class Agent {
  name: string;
  sessions: Session[] = [];

  constructor(name: string) {
    this.name = name;
  }

  createSession(type: string): Session {
    const session = new Session(type);
    this.sessions.push(session);
    return session;
  }
}
```

### `src/Session.ts`
```typescript
export class Session {
  type: string;

  constructor(type: string) {
    this.type = type;
  }

  process(input: string): string {
    return `Processed: ${input}`;
  }
}
```

### `src/Message.ts`
```typescript
export interface Message {
  from: string;
  text: string;
}
```

### `src/index.ts`
```typescript
export { Agent } from './Agent';
export { Session } from './Session';
export type { Message } from './Message';
```

---

## C. Create tests

### `test/basic.test.ts`
```typescript
import { Agent, Session } from '../src/index';

describe('Basic tests', () => {
  test('Create an Agent', () => {
    const agent = new Agent('TestAgent');
    expect(agent.name).toBe('TestAgent');
    expect(agent.sessions).toHaveLength(0);
  });

  test('Create a Session', () => {
    const session = new Session('meeting');
    expect(session.type).toBe('meeting');
  });

  test('Call process() returns a string', () => {
    const session = new Session('test');
    const result = session.process('hello');
    expect(typeof result).toBe('string');
    expect(result).toBe('Processed: hello');
  });

  test('Agent creates and stores sessions', () => {
    const agent = new Agent('Alice');
    const session = agent.createSession('chat');
    expect(agent.sessions).toHaveLength(1);
    expect(session.type).toBe('chat');
  });
});
```

---

## D. Build + test run

Run in order:
1. `npm run build`
2. `npm test`

Confirm all tests pass.

---

## E. Create sandbox folder

The sandbox is the **lightweight coding surface**.  
AI coding agent must work **only inside this folder** for all new features.

### Files to create (empty shells)

```
sandbox/
  AgentSandbox.ts
  SessionSandbox.ts
  Meeting.ts
  SessionSelector.ts
  SandboxRunner.ts
```

### Rules for sandbox
- Sandbox classes **extend** the real classes in `src/`
- No wrappers, no modification to `src/`
- All new logic lives in `sandbox/`
- AI must not scan or modify `src/` or `test/`

---

## 🛑 STOP POINT A

Stop after creating the sandbox folder and empty files.  
Do not implement sandbox logic yet.

---

## F. Implement sandbox classes

### `sandbox/SessionSelector.ts`
```typescript
import { Agent } from '../src/Agent';
import { Session } from '../src/Session';
import { SessionSandbox } from './SessionSandbox';

export class SessionSelector {
  selectSession(agent: Agent, type: string): SessionSandbox {
    const existing = agent.sessions.find(s => s.type === type);
    if (existing) {
      return existing as SessionSandbox;
    }
    const newSession = new SessionSandbox(type);
    agent.sessions.push(newSession);
    return newSession;
  }
}
```

### `sandbox/SessionSandbox.ts`
```typescript
import { Session } from '../src/Session';

export class SessionSandbox extends Session {
  private agentName: string = 'Unknown';

  setAgentName(name: string): void {
    this.agentName = name;
  }

  process(input: string): string {
    return `Hi ${input} Nice to meet you! I'm ${this.agentName}.`;
  }
}
```

### `sandbox/AgentSandbox.ts`
```typescript
import { Agent } from '../src/Agent';

export class AgentSandbox extends Agent {
  getMeetingSession() {
    return null;
  }
}
```

### `sandbox/Meeting.ts`
```typescript
import { AgentSandbox } from './AgentSandbox';
import { SessionSelector } from './SessionSelector';
import { SessionSandbox } from './SessionSandbox';

export class Meeting {
  private agents: AgentSandbox[] = [];
  private selector: SessionSelector = new SessionSelector();

  addAgent(agent: AgentSandbox): void {
    const existingNames = this.agents.map(a => a.name);
    const session = this.selector.selectSession(agent, 'meeting') as SessionSandbox;
    session.setAgentName(agent.name);
    
    const greeting = existingNames.length === 0 
      ? `Hello everyone! I'm ${agent.name}.`
      : `Hello! I'm ${agent.name}.`;
    
    console.log(`${agent.name} joins the meeting`);
    console.log(`${agent.name}: ${greeting}`);
    
    for (const existingAgent of this.agents) {
      const existingSession = this.selector.selectSession(existingAgent, 'meeting') as SessionSandbox;
      const response = existingSession.process(agent.name);
      console.log(`${existingAgent.name}: ${response}`);
    }
    
    this.agents.push(agent);
  }

  getAgents(): AgentSandbox[] {
    return this.agents;
  }

  broadcastGreetings(): void {
    console.log('=== Meeting Started ===\n');
  }
}
```

### `sandbox/SandboxRunner.ts`
```typescript
import { AgentSandbox } from './AgentSandbox';
import { Meeting } from './Meeting';

class SandboxRunner {
  static run(): void {
    const alice = new AgentSandbox('Alice');
    const bob = new AgentSandbox('Bob');
    const carol = new AgentSandbox('Carol');

    const meeting = new Meeting();
    meeting.broadcastGreetings();
    
    meeting.addAgent(alice);
    console.log('');
    meeting.addAgent(bob);
    console.log('');
    meeting.addAgent(carol);

    console.log('\n=== Meeting Complete ===\n');
  }
}

SandboxRunner.run();
```

---

## G. Build + run sandbox

Run in order:
1. `npm run build`
2. `npm run run:sandbox`

### Expected Output

```
=== Meeting Started ===

Alice joins the meeting
Alice: Hello everyone! I'm Alice.

Bob joins the meeting
Bob: Hello! I'm Bob.
Alice: Hi Bob Nice to meet you! I'm Alice.

Carol joins the meeting
Carol: Hello! I'm Carol.
Alice: Hi Carol Nice to meet you! I'm Alice.
Bob: Hi Carol Nice to meet you! I'm Bob.

=== Meeting Complete ===
```

---

## End of POC

This POC gives you:
- A clean Node + TS project
- Minimal real classes
- A fully isolated sandbox
- Inheritance-based overrides
- A safe environment to evolve your multi-agent meeting concept
