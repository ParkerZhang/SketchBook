import { Agent } from '../src';
import { SessionSandbox } from './SessionSandbox';

export class SessionSelector {
  private agent: Agent;
  private meetingSession: SessionSandbox | null = null;

  constructor(agent: Agent) {
    this.agent = agent;
  }

  getMeetingSession(): SessionSandbox {
    if (!this.meetingSession) {
      this.meetingSession = new SessionSandbox();
      this.agent.sessions.push(this.meetingSession);
    }
    return this.meetingSession;
  }
}
