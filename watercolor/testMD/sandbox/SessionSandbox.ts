import { Session } from '../src/Session';

export class SessionSandbox extends Session {
  private agentName: string = 'Unknown';
  private history: string[] = [];

  setAgentName(name: string): void {
    this.agentName = name;
  }

  process(input: string): string {
    return `Hi ${input} Nice to meet you! I'm ${this.agentName}.`;
  }

  chat(message: string): void {
    this.history.push(`${this.agentName}: ${message}`);
  }

  listen(message: string): void {
    this.history.push(message);
  }

  response(): string {
    return this.history.join('\n');
  }
}
