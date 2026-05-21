"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.SessionSandbox = void 0;
const Session_1 = require("../src/Session");
class SessionSandbox extends Session_1.Session {
    constructor(agentId, type) {
        super(agentId, type);
        this.agentName = 'Unknown';
        this.history = [];
    }
    setAgentName(name) {
        this.agentName = name;
    }
    process(input) {
        return `Hi ${input} Nice to meet you! I'm ${this.agentName}.`;
    }
    chat(message) {
        this.history.push(`Chat: ${message}`);
    }
    listen(message) {
        this.history.push(`Listen: ${message}`);
    }
    response() {
        return `Response from ${this.agentName}`;
    }
}
exports.SessionSandbox = SessionSandbox;
//# sourceMappingURL=SessionSandbox.js.map