import { Agent } from '../src';
import { SessionSandbox } from './SessionSandbox';
import { SessionSelector } from './SessionSelector';

export class AgentSandbox extends Agent {
  private sessionSelector: SessionSelector;

  constructor(name: string) {
    super(name);
    this.sessionSelector = new SessionSelector(this);
  }

  getMeetingSession(): SessionSandbox {
    return this.sessionSelector.getMeetingSession();
  }
}
