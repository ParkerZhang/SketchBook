import { Agent } from '../src/Agent';

export class AgentSandbox extends Agent {
  constructor(agentId: string, name: string, workspace?: string) {
    super(agentId, name, workspace);
  }

  getMeetingSession() {
    return null;
  }
}
