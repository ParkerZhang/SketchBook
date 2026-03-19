import { MeetingEngine } from './MeetingEngine';
import { AgentSandbox } from './AgentSandbox';

export class Demo {
  static run(): void {
    console.log('═══════════════════════════════════════════════');
    console.log('          STAGE 4 DEMO - Meeting System        ');
    console.log('═══════════════════════════════════════════════\n');

    this.runShortMeeting();

    console.log('\n');

    this.runLongMeeting();
  }

  static runShortMeeting(): void {
    console.log('┌─────────────────────────────────────────────┐');
    console.log('│  DEMO 1: Short Meeting                      │');
    console.log('└─────────────────────────────────────────────┘\n');

    const engine = new MeetingEngine();

    engine.handleExternalCommand('start "Quick Sync"');
    const meeting = engine.getMeeting('Quick Sync')!;

    const alice = new AgentSandbox('agent-alice', 'Alice');
    const bob = new AgentSandbox('agent-bob', 'Bob');

    meeting.addAgent(alice);
    meeting.addAgent(bob);

    console.log('\n--- Meeting Log ---');
    console.log(meeting.log.join('\n'));

    console.log('\n--- Discussion ---');
    meeting.discuss(alice, 'The deployment is ready!');
    meeting.discuss(bob, 'Great, I\'ll review the PR.');
    console.log(meeting.log.slice(meeting.log.indexOf('Alice: Hi The deployment')).join('\n'));

    engine.handleExternalCommand('note "Quick Sync" "Deployment approved"');
    engine.handleExternalCommand('end "Quick Sync"');
  }

  static runLongMeeting(): void {
    console.log('┌─────────────────────────────────────────────┐');
    console.log('│  DEMO 2: Long Meeting (stopped & resumed)   │');
    console.log('└─────────────────────────────────────────────┘\n');

    const engine = new MeetingEngine();

    engine.handleExternalCommand('start "Sprint Planning"');
    const meeting = engine.getMeeting('Sprint Planning')!;

    console.log('--- Agents Joining ---');
    const alice = new AgentSandbox('agent-alice', 'Alice');
    const bob = new AgentSandbox('agent-bob', 'Bob');
    const carol = new AgentSandbox('agent-carol', 'Carol');
    const dave = new AgentSandbox('agent-dave', 'Dave');

    meeting.addAgent(alice);
    meeting.addAgent(bob);
    meeting.addAgent(carol);
    meeting.addAgent(dave);

    console.log(meeting.log.join('\n'));

    console.log('\n--- Discussion Session 1 ---');
    meeting.discuss(carol, 'Let\'s prioritize the new API feature.');
    meeting.discuss(alice, 'I agree, but we also need to fix the login bug.');
    meeting.discuss(bob, 'The bug affects 20% of users. Should be critical.');

    const idx = meeting.log.length;
    engine.handleExternalCommand('note "Sprint Planning" "API feature is P1"');
    engine.handleExternalCommand('note "Sprint Planning" "Login bug is critical"');
    console.log(meeting.log.slice(idx).join('\n'));

    console.log('\n--- Meeting Paused (Lunch Break) ---\n');
    engine.handleExternalCommand('stop "Sprint Planning"');

    console.log('--- Meeting Resumed (After Lunch) ---\n');
    engine.handleExternalCommand('resume "Sprint Planning"');
    console.log(meeting.log.slice(idx).join('\n'));

    console.log('\n--- Discussion Session 2 ---');
    const idx2 = meeting.log.length;
    meeting.discuss(dave, 'I\'ll take the login bug fix.');
    meeting.discuss(alice, 'Thanks Dave! I\'ll handle the API design.');
    meeting.discuss(carol, 'Bob and I will work on the backend implementation.');
    console.log(meeting.log.slice(idx2).join('\n'));

    engine.handleExternalCommand('note "Sprint Planning" "Dave: login bug"');
    engine.handleExternalCommand('note "Sprint Planning" "Alice: API design"');
    engine.handleExternalCommand('note "Sprint Planning" "Bob+Carol: backend"');

    engine.handleExternalCommand('end "Sprint Planning"');
  }
}

Demo.run();
