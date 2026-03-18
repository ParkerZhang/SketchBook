import { Session } from '../src';

export class SessionSandbox extends Session {
  constructor() {
    super('meeting');
  }

  process(input: string): string {
    return `[Meeting] meeting: ${input}`;
  }
}
