import { Session } from '../src/Session';
export declare class SessionSandbox extends Session {
    private agentName;
    private history;
    constructor(agentId: string, type: string);
    setAgentName(name: string): void;
    process(input: string): string;
    chat(message: string): void;
    listen(message: string): void;
    response(): string;
}
//# sourceMappingURL=SessionSandbox.d.ts.map