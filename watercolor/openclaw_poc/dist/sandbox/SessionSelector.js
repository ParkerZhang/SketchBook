"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.SessionSelector = void 0;
const SessionSandbox_1 = require("./SessionSandbox");
class SessionSelector {
    selectSession(agent, type) {
        const existing = agent.sessions.find(s => s.type === type);
        if (existing) {
            return existing;
        }
        const newSession = new SessionSandbox_1.SessionSandbox(agent.agentId, type);
        agent.sessions.push(newSession);
        return newSession;
    }
}
exports.SessionSelector = SessionSelector;
//# sourceMappingURL=SessionSelector.js.map