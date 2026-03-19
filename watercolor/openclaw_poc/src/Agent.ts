import { Session } from './Session';
import { Message } from './Message';

export class Agent {
  agentId: string;
  name: string;
  sessions: Session[] = [];
  workspace: string;

  constructor(agentId: string, name: string, workspace?: string) {
    this.agentId = agentId;
    this.name = name;
    this.workspace = workspace || '';
  }

  createSession(type: string): Session {
    const session = new Session(this.agentId, type);
    this.sessions.push(session);
    return session;
  }

  getSession(sessionId: string): Session | undefined {
    return this.sessions.find(s => s.sessionId === sessionId);
  }

  sendMessage(toAgentId: string, text: string): void {
    console.log(`Message from ${this.agentId} to ${toAgentId}: ${text}`);
  }
}
