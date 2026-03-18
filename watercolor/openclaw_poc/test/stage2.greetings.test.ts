import { Meeting } from '../sandbox/Meeting';
import { AgentSandbox } from '../sandbox/AgentSandbox';

describe('Stage 2 - Greetings Tests', () => {
  test('Agent joins are logged', () => {
    const meeting = new Meeting('Test Meeting');
    const agent = new AgentSandbox('Alice');
    meeting.addAgent(agent);
    
    expect(meeting.log[0]).toContain('Alice joins the meeting.');
  });

  test('Agents respond when joining', () => {
    const meeting = new Meeting('Test Meeting');
    const agent = new AgentSandbox('Bob');
    meeting.addAgent(agent);
    
    expect(meeting.log.some(l => l.includes('Bob:'))).toBe(true);
    expect(meeting.log.some(l => l.includes('Nice to meet you!'))).toBe(true);
  });

  test('Existing agents greet newcomer', () => {
    const meeting = new Meeting('Test Meeting');
    const alice = new AgentSandbox('Alice');
    const bob = new AgentSandbox('Bob');
    
    meeting.addAgent(alice);
    meeting.addAgent(bob);
    
    expect(meeting.log.some(l => l.includes('Alice:') && l.includes('Bob'))).toBe(true);
  });

  test('resumeGreetings() logs resuming', () => {
    const meeting = new Meeting('Test Meeting');
    const agent = new AgentSandbox('Alice');
    meeting.addAgent(agent);
    
    meeting.resumeGreetings();
    
    expect(meeting.log.some(l => l.includes('Meeting Resumed'))).toBe(true);
    expect(meeting.log.some(l => l.includes('is back!'))).toBe(true);
  });

  test('Full meeting lifecycle', () => {
    const meeting = new Meeting('Lifecycle Test');
    
    const alice = new AgentSandbox('Alice');
    const bob = new AgentSandbox('Bob');
    
    meeting.addAgent(alice);
    meeting.addAgent(bob);
    
    expect(meeting.getAgentCount()).toBe(2);
    expect(meeting.log.length).toBeGreaterThan(0);
  });
});
