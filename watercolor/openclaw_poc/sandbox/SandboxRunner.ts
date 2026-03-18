import { AgentSandbox } from './AgentSandbox';
import { MeetingEngine } from './MeetingEngine';

const engine = new MeetingEngine();

engine.sendCommand({ type: 'start', subject: 'Project Review' });
engine.tick();

const meeting = engine.getMeeting('Project Review')!;

const alice = new AgentSandbox('Alice');
const bob = new AgentSandbox('Bob');
const carol = new AgentSandbox('Carol');

meeting.addAgent(alice);
meeting.addAgent(bob);
meeting.addAgent(carol);

console.log('\n--- Meeting Log ---\n');
console.log(meeting.log.join('\n'));

engine.sendCommand({ type: 'note', subject: 'Project Review', text: 'Discuss Q1 goals' });
engine.tick();

console.log('\n--- Pausing Meeting ---\n');
engine.sendCommand({ type: 'stop', subject: 'Project Review' });
engine.tick();

console.log('\n--- Resuming Meeting ---\n');
engine.sendCommand({ type: 'resume', subject: 'Project Review' });
engine.tick();

console.log('\n--- Final Log ---\n');
console.log(meeting.log.join('\n'));

console.log('\n--- Ending Meeting ---\n');
engine.sendCommand({ type: 'end', subject: 'Project Review' });
engine.tick();
