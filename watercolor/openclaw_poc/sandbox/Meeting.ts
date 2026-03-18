import { AgentSandbox } from './AgentSandbox';

export class Meeting {
  private agents: AgentSandbox[] = [];

  addAgent(agent: AgentSandbox): void {
    this.agents.push(agent);
  }

  broadcastGreetings(): void {
    for (const agent of this.agents) {
      const session = agent.getMeetingSession();
      console.log(`${agent.name} joins the meeting.`);
      console.log(`${agent.name}: ${session.process('Hello everyone!')}`);
    }

    console.log('\n--- Agent Responses ---');
    for (const agent of this.agents) {
      const session = agent.getMeetingSession();
      const responses = this.agents
        .filter(a => a !== agent)
        .map(a => `${a.name} says hello`);
      
      const response = session.process(`Greetings from: ${responses.join(', ')}`);
      console.log(`${agent.name}: ${response}`);
    }
  }

  getAgentCount(): number {
    return this.agents.length;
  }
}
