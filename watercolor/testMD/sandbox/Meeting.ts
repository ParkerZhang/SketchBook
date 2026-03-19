import { AgentSandbox } from './AgentSandbox';
import { SessionSelector } from './SessionSelector';
import { SessionSandbox } from './SessionSandbox';

export class Meeting {
  subject: string;
  active: boolean;
  notes: string[];
  agents: AgentSandbox[];
  log: string[];
  private selector: SessionSelector = new SessionSelector();

  constructor(subject: string) {
    this.subject = subject;
    this.active = false;
    this.notes = [];
    this.agents = [];
    this.log = [];
  }

  tick(): void {
    // Empty for now
  }

  addNote(text: string): void {
    this.notes.push(text);
  }

  summarize(): string {
    return `Meeting: ${this.subject}\nAgents: ${this.agents.length}\nNotes: ${this.notes.length}`;
  }

  addAgent(agent: AgentSandbox): void {
    const session = this.selector.selectSession(agent, 'meeting') as SessionSandbox;
    session.setAgentName(agent.name);
    
    this.log.push(`${agent.name} joins the meeting.`);
    
    const greeting = session.process('Hello everyone!');
    this.log.push(`${agent.name}: ${greeting}`);
    
    for (const existingAgent of this.agents) {
      const existingSession = this.selector.selectSession(existingAgent, 'meeting') as SessionSandbox;
      const response = existingSession.process(agent.name);
      this.log.push(`${existingAgent.name}: ${response}`);
    }
    
    this.agents.push(agent);
  }

  resumeGreetings(): void {
    this.log.push('--- Meeting Resumed ---');
    for (const agent of this.agents) {
      const session = this.selector.selectSession(agent, 'meeting') as SessionSandbox;
      this.log.push(`${agent.name} is back!`);
    }
    for (const agent of this.agents) {
      const session = this.selector.selectSession(agent, 'meeting') as SessionSandbox;
      for (const other of this.agents) {
        if (other !== agent) {
          const response = session.process(other.name);
          this.log.push(`${agent.name} greets ${other.name}: ${response}`);
        }
      }
    }
  }

  getAgentCount(): number {
    return this.agents.length;
  }

  discuss(agent: AgentSandbox, message: string): void {
    const session = this.selector.selectSession(agent, 'meeting') as SessionSandbox;
    session.setAgentName(agent.name);
    const response = session.process(message);
    this.log.push(`${agent.name}: ${response}`);
  }
}
