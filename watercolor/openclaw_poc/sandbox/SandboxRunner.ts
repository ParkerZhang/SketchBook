import { AgentSandbox } from './AgentSandbox';
import { Meeting } from './Meeting';

export class SandboxRunner {
  static run(): void {
    console.log('=== Sandbox Demo: Multi-Agent Meeting ===\n');

    const alice = new AgentSandbox('Alice');
    const bob = new AgentSandbox('Bob');
    const charlie = new AgentSandbox('Charlie');

    const meeting = new Meeting();
    meeting.addAgent(alice);
    meeting.addAgent(bob);
    meeting.addAgent(charlie);

    console.log(`Meeting started with ${meeting.getAgentCount()} agents.\n`);
    meeting.broadcastGreetings();

    console.log('\n=== Session Reuse Check ===');
    const aliceSession1 = alice.getMeetingSession();
    const aliceSession2 = alice.getMeetingSession();
    console.log(`Alice's sessions: ${alice.sessions.length}`);
    console.log(`Session reuse works: ${aliceSession1 === aliceSession2}`);

    console.log('\n=== Demo Complete ===');
  }
}

SandboxRunner.run();
