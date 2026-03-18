import { AgentSandbox } from './AgentSandbox';
import { Meeting } from './Meeting';

class SandboxRunner {
  static run(): void {
    const alice = new AgentSandbox('Alice');
    const bob = new AgentSandbox('Bob');
    const carol = new AgentSandbox('Carol');

    const meeting = new Meeting();
    meeting.broadcastGreetings();
    
    meeting.addAgent(alice);
    console.log('');
    meeting.addAgent(bob);
    console.log('');
    meeting.addAgent(carol);

    console.log('\n=== Meeting Complete ===\n');
  }
}

SandboxRunner.run();
