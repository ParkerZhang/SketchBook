import { AgentSandbox } from './AgentSandbox';
export declare class Meeting {
    subject: string;
    active: boolean;
    notes: string[];
    agents: AgentSandbox[];
    log: string[];
    private selector;
    constructor(subject: string);
    tick(): void;
    addNote(text: string): void;
    summarize(): string;
    addAgent(agent: AgentSandbox): void;
    resumeGreetings(): void;
    getAgentCount(): number;
    discuss(agent: AgentSandbox, message: string): void;
}
//# sourceMappingURL=Meeting.d.ts.map