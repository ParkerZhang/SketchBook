import { Agent } from '../src/Agent';

export class AgentSandbox extends Agent {
  getMeetingSession() {
    return null;
  }
}

export function createAgentSandbox(name: string): AgentSandbox {
  const id = `agent-${name.toLowerCase()}-${Date.now()}`;
  return new AgentSandbox(id, name);
}
