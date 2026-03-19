"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Meeting = void 0;
const SessionSelector_1 = require("./SessionSelector");
class Meeting {
    constructor(subject) {
        this.selector = new SessionSelector_1.SessionSelector();
        this.subject = subject;
        this.active = false;
        this.notes = [];
        this.agents = [];
        this.log = [];
    }
    tick() {
    }
    addNote(text) {
        this.notes.push(text);
    }
    summarize() {
        return `Meeting: ${this.subject}\nAgents: ${this.agents.length}\nNotes: ${this.notes.length}`;
    }
    addAgent(agent) {
        const session = this.selector.selectSession(agent, 'meeting');
        session.setAgentName(agent.name);
        this.log.push(`${agent.name} joins the meeting.`);
        const greeting = session.process('Hello everyone!');
        this.log.push(`${agent.name}: ${greeting}`);
        for (const existingAgent of this.agents) {
            const existingSession = this.selector.selectSession(existingAgent, 'meeting');
            const response = existingSession.process(agent.name);
            this.log.push(`${existingAgent.name}: ${response}`);
        }
        this.agents.push(agent);
    }
    resumeGreetings() {
        this.log.push('--- Meeting Resumed ---');
        for (const agent of this.agents) {
            const session = this.selector.selectSession(agent, 'meeting');
            this.log.push(`${agent.name} is back!`);
        }
        for (const agent of this.agents) {
            const session = this.selector.selectSession(agent, 'meeting');
            for (const other of this.agents) {
                if (other !== agent) {
                    const response = session.process(other.name);
                    this.log.push(`${agent.name} greets ${other.name}: ${response}`);
                }
            }
        }
    }
    getAgentCount() {
        return this.agents.length;
    }
    discuss(agent, message) {
        const session = this.selector.selectSession(agent, 'meeting');
        session.setAgentName(agent.name);
        const response = session.process(message);
        this.log.push(`${agent.name}: ${response}`);
    }
}
exports.Meeting = Meeting;
//# sourceMappingURL=Meeting.js.map