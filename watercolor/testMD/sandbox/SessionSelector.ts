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
