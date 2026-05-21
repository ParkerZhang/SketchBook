"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.AgentSandbox = void 0;
const Agent_1 = require("../src/Agent");
class AgentSandbox extends Agent_1.Agent {
    constructor(agentId, name, workspace) {
        super(agentId, name, workspace);
    }
    getMeetingSession() {
        return null;
    }
}
exports.AgentSandbox = AgentSandbox;
//# sourceMappingURL=AgentSandbox.js.map