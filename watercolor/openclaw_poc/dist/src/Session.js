"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Session = void 0;
class Session {
    constructor(agentId, type) {
        this.messages = [];
        this.active = true;
        this.sessionId = `${agentId}-${type}-${Date.now()}`;
        this.agentId = agentId;
        this.type = type;
    }
    addMessage(message) {
        this.messages.push(message);
    }
    process(input) {
        return `Processed: ${input}`;
    }
}
exports.Session = Session;
//# sourceMappingURL=Session.js.map