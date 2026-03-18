"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.MeetingEngine = void 0;
const Meeting_1 = require("./Meeting");
class MeetingEngine {
    constructor() {
        this.meetings = new Map();
        this.commandQueue = [];
    }
    sendCommand(command) {
        this.commandQueue.push(command);
    }
    tick() {
        while (this.commandQueue.length > 0) {
            const command = this.commandQueue.shift();
            this.dispatch(command);
        }
    }
    dispatch(command) {
        const meeting = this.meetings.get(command.subject);
        switch (command.type) {
            case 'start':
                const newMeeting = new Meeting_1.Meeting(command.subject);
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
    getMeeting(subject) {
        return this.meetings.get(subject);
    }
}
exports.MeetingEngine = MeetingEngine;
//# sourceMappingURL=MeetingEngine.js.map