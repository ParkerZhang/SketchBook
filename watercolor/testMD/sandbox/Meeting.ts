import { AgentSandbox } from './AgentSandbox';
import { SessionSelector } from './SessionSelector';
import { SessionSandbox } from './SessionSandbox';

export class Meeting {
  private agents: AgentSandbox[] = [];
  private selector: SessionSelector = new SessionSelector();

  addAgent(agent: AgentSandbox): void {
    const existingNames = this.agents.map(a => a.name);
    const session = this.selector.selectSession(agent, 'meeting') as SessionSandbox;
    session.setAgentName(agent.name);
    
    const greeting = existingNames.length === 0 
      ? `Hello everyone! I'm ${agent.name}.`
      : `Hello! I'm ${agent.name}.`;
    
    console.log(`${agent.name} joins the meeting`);
    console.log(`${agent.name}: ${greeting}`);
    
    for (const existingAgent of this.agents) {
      const existingSession = this.selector.selectSession(existingAgent, 'meeting') as SessionSandbox;
      const response = existingSession.process(agent.name);
      console.log(`${existingAgent.name}: ${response}`);
    }
    
    this.agents.push(agent);
  }

  getAgents(): AgentSandbox[] {
    return this.agents;
  }

  broadcastGreetings(): void {
    console.log('=== Meeting Started ===\n');
  }
}
