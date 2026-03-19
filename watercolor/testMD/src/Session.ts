import { Message } from './Message';

export class Session {
  sessionId: string;
  agentId: string;
  type: string;
  messages: Message[] = [];
  active: boolean = true;

  constructor(agentId: string, type: string) {
    this.agentId = agentId;
    this.type = type;
    this.sessionId = `${agentId}-${type}-${Date.now()}`;
  }

  addMessage(message: Message): void {
    this.messages.push(message);
  }

  process(input: string): string {
    return `Processed: ${input}`;
  }
}
