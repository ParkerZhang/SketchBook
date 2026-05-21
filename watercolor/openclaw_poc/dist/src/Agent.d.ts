import { Session } from './Session';
export declare class Agent {
    agentId: string;
    name: string;
    sessions: Session[];
    workspace: string;
    constructor(agentId: string, name: string, workspace?: string);
    createSession(type: string): Session;
    getSession(sessionId: string): Session | undefined;
    sendMessage(toAgentId: string, text: string): void;
}
//# sourceMappingURL=Agent.d.ts.map