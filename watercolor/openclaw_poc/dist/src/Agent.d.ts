import { Session } from './Session';
export declare class Agent {
    name: string;
    sessions: Session[];
    constructor(name: string);
    createSession(type: string): Session;
}
//# sourceMappingURL=Agent.d.ts.map