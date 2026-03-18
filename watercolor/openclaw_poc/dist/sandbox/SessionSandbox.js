"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.SessionSandbox = void 0;
const Session_1 = require("../src/Session");
class SessionSandbox extends Session_1.Session {
    constructor() {
        super(...arguments);
        this.agentName = 'Unknown';
    }
    setAgentName(name) {
        this.agentName = name;
    }
    process(input) {
        return `Hi ${input} Nice to meet you! I'm ${this.agentName}.`;
    }
}
exports.SessionSandbox = SessionSandbox;
//# sourceMappingURL=SessionSandbox.js.map