import { Meeting } from './Meeting';
import { Command } from './Command';

export class MeetingEngine {
  private meetings: Map<string, Meeting> = new Map();
  private commandQueue: Command[] = [];

  sendCommand(command: Command): void {
    this.commandQueue.push(command);
  }

  tick(): void {
    while (this.commandQueue.length > 0) {
      const command = this.commandQueue.shift()!;
      this.dispatch(command);
    }
  }

  public handleExternalCommand(input: string): void {
    const command = this.parse(input);
    this.sendCommand(command);
    this.tick();
  }

  private parse(input: string): Command {
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

  private dispatch(command: Command): void {
    const meeting = this.meetings.get(command.subject);

    switch (command.type) {
      case 'start':
        const newMeeting = new Meeting(command.subject);
        newMeeting.active = true;
        this.meetings.set(command.subject, newMeeting);
        console.log(`Meeting '${command.subject}' started.`);
        break;
      case 'stop':
        if (meeting) {
          meeting.active = false;
          console.log(`Meeting '${command.subject}' paused.`);
        }
        break;
      case 'resume':
        if (meeting) {
          meeting.active = true;
          meeting.resumeGreetings();
          console.log(`Meeting '${command.subject}' resumed.`);
        }
        break;
      case 'note':
        if (meeting) {
          meeting.addNote(command.text);
          console.log(`Note: ${command.text}`);
        }
        break;
      case 'end':
        if (meeting) {
          console.log(meeting.summarize());
          this.meetings.delete(command.subject);
          console.log(`Meeting '${command.subject}' ended.`);
        }
        break;
    }
  }

  getMeeting(subject: string): Meeting | undefined {
    return this.meetings.get(subject);
  }
}
