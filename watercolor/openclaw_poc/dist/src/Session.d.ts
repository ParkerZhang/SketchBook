import { Message } from './Message';
export declare class Session {
    sessionId: string;
    agentId: string;
    type: string;
    messages: Message[];
    active: boolean;
    constructor(agentId: string, type: string);
    addMessage(message: Message): void;
    process(input: string): string;
}
//# sourceMappingURL=Session.d.ts.map