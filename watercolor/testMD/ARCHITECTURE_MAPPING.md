# Architecture Mapping Summary

## Learning Process

Used Q/A protocol (SAND_BOX_QA.md) with skeleton.md and distiller.md to map sandbox concepts to real OpenClaw architecture.

---

## Real Production Classes

| Sandbox Concept | Real Class | File | Key Details |
|-----------------|------------|------|-------------|
| **Agent** | `Agent` | `src/Agent.ts` | Has sessions, workspace, isolated |
| **Session** | `Session` | `src/Session.ts` | Conversation context, processes messages |
| **Message** | `Message` (interface) | `src/Message.ts` | Wire protocol between agents |
| **Engine** | `MeetingEngine` | `sandbox/` | New class (no existing engine in POC) |

---

## Agent Class (Updated)

```typescript
export class Agent {
  agentId: string;      // Unique identifier
  name: string;         // Display name
  sessions: Session[]; // Conversation contexts
  workspace: string;    // Isolated work directory

  constructor(agentId: string, name: string, workspace?: string);
  createSession(type: string): Session;
  getSession(sessionId: string): Session | undefined;
  sendMessage(toAgentId: string, text: string): void;
}
```

---

## Session Class (Updated)

```typescript
export class Session {
  sessionId: string;       // Unique identifier
  agentId: string;          // Owner agent
  type: string;            // Session type (meeting, chat, etc.)
  messages: Message[];      // Message history
  active: boolean;          // Active flag

  constructor(agentId: string, type: string);
  addMessage(message: Message): void;
  process(input: string): string;
}
```

---

## Message Interface (Enhanced)

```typescript
export interface Message {
  id: string;
  from: string;
  to?: string;
  text: string;
  timestamp: number;
  type?: string;
}
```

---

## Sandbox Extension Pattern

### AgentSandbox extends Agent
```typescript
export class AgentSandbox extends Agent {
  getMeetingSession() {
    return null;
  }
}
```

### SessionSandbox extends Session
```typescript
export class SessionSandbox extends Session {
  private agentName: string = 'Unknown';
  private history: string[] = [];

  setAgentName(name: string): void;
  process(input: string): string;  // Overridden for greetings
  chat(message: string): void;
  listen(message: string): void;
  response(): string;
}
```

---

## Key Learnings

1. **Gateway loads agents from openclaw.json** - Agent config contains workspace
2. **Each Agent is isolated** - Has own sessions and workspace
3. **Agents can spawn subagents** - For agent-to-agent communication
4. **Meeting adds collaboration** - Lets multiple agents discuss together
5. **Sandbox never modifies src/** - All changes stay in sandbox/ and test/

---

## File Structure

```
src/                          # Real production classes
├── Agent.ts                  # Main Agent class
├── Session.ts                # Session class
├── Message.ts                # Message interface
└── index.ts                  # Exports

sandbox/                      # Experimental sandbox
├── AgentSandbox.ts           # Extends Agent
├── SessionSandbox.ts         # Extends Session
├── Meeting.ts                # Meeting collaboration
├── MeetingEngine.ts          # Command-driven engine
├── SessionSelector.ts        # Session management
├── Command.ts                # Command types
├── CLI.ts                    # CLI interface
├── Demo.ts                   # Demo scenarios
└── SandboxRunner.ts          # Entry point

test/                         # Tests (mirror sandbox structure)
├── basic.test.ts
├── stage2.greetings.test.ts
├── stage3.demoFlow.test.ts
└── stage4.cli.test.ts
```

---

## Command Types (MeetingEngine)

```typescript
type Command =
  | { type: "start"; subject: string }
  | { type: "stop"; subject: string }
  | { type: "resume"; subject: string }
  | { type: "note"; subject: string; text: string }
  | { type: "end"; subject: string };
```

---

## Architecture Summary

```
OpenClaw Gateway
    └── Loads agents from openclaw.json
            └── Agent (isolated, has workspace, sessions)
                    └── Session (conversation context)
                            └── Message (wire protocol)

Sandbox Extension:
    └── AgentSandbox (extends Agent) + Meeting capabilities
    └── SessionSandbox (extends Session) + Greetings/chat
    └── MeetingEngine (manages meetings + commands)
```
