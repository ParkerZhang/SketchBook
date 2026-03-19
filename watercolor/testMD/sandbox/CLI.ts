import { Command } from './Command';
import { MeetingEngine } from './MeetingEngine';

export class CLI {
  constructor(private engine: MeetingEngine) {}

  static parse(input: string): Command {
    const trimmed = input.trim();
    const match = trimmed.match(/^(\w+)\s+"([^"]*)"(?:\s+"([^"]*)")?$/);
    
    if (!match) {
      throw new Error(`Invalid command format: ${input}`);
    }
    
    const [, cmdType, subject, text] = match;
    
    switch (cmdType.toLowerCase()) {
      case 'start':
        return { type: 'start', subject };
      case 'stop':
        return { type: 'stop', subject };
      case 'resume':
        return { type: 'resume', subject };
      case 'note':
        if (!text) throw new Error('Note command requires text');
        return { type: 'note', subject, text };
      case 'end':
        return { type: 'end', subject };
      default:
        throw new Error(`Unknown command: ${cmdType}`);
    }
  }

  handle(input: string): void {
    this.engine.handleExternalCommand(input);
  }

  start(): void {
    console.log('CLI stdin listener not implemented in Stage 4.');
  }
}
