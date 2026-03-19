import { Session } from '../src/Session';

export class SessionSandbox extends Session {
  private agentName: string = 'Unknown';
  private history: string[] = [];

  constructor(agentId: string, type: string) {
    super(agentId, type);
  }

  setAgentName(name: string): void {
    this.agentName = name;
  }

  process(input: string): string {
    return `Hi ${input} Nice to meet you! I'm ${this.agentName}.`;
  }

  chat(message: string): void {
    this.history.push(`Chat: ${message}`);
  }

  listen(message: string): void {
    this.history.push(`Listen: ${message}`);
  }

  response(): string {
    return `Response from ${this.agentName}`;
  }
}
