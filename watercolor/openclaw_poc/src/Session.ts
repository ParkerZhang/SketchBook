export class Session {
  type: string;

  constructor(type: string) {
    this.type = type;
  }

  process(input: string): string {
    return `Processed: ${input}`;
  }
}