import { Meeting } from './Meeting';
import { Command } from './Command';
export declare class MeetingEngine {
    private meetings;
    private commandQueue;
    sendCommand(command: Command): void;
    tick(): void;
    private dispatch;
    getMeeting(subject: string): Meeting | undefined;
}
//# sourceMappingURL=MeetingEngine.d.ts.map