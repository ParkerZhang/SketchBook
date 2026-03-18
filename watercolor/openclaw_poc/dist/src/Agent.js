"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Agent = void 0;
const Session_1 = require("./Session");
class Agent {
    constructor(name) {
        this.name = name;
        this.sessions = [];
    }
    createSession(type) {
        const session = new Session_1.Session(type);
        this.sessions.push(session);
        return session;
    }
}
exports.Agent = Agent;
//# sourceMappingURL=Agent.js.map