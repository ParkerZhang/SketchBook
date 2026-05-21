"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Agent = void 0;
const Session_1 = require("./Session");
class Agent {
    constructor(agentId, name, workspace) {
        this.sessions = [];
        this.agentId = agentId;
        this.name = name;
        this.workspace = workspace || '';
    }
    createSession(type) {
        const session = new Session_1.Session(this.agentId, type);
        this.sessions.push(session);
        return session;
    }
    getSession(sessionId) {
        return this.sessions.find(s => s.sessionId === sessionId);
    }
    sendMessage(toAgentId, text) {
        console.log(`Message from ${this.agentId} to ${toAgentId}: ${text}`);
    }
}
exports.Agent = Agent;
//# sourceMappingURL=Agent.js.map