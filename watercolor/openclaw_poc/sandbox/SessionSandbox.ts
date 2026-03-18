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
