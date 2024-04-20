package scketch.anatomy;

import java.io.*;

public class MemJson {
    private String _name;
    private String _file;
    private byte[][] _mem;
    private int[][] _index;


    protected static int[][] _index_cache = new int[7000000][2];

    public MemJson(String name , String[] params) throws IOException {
        _name= name;
        _file = params[0];
        int mem_size1 = Integer.parseInt(params[1]);
        int mem_size2 = Integer.parseInt(params[2]);
        _mem = new byte[mem_size1][mem_size2];
        load();

    }
    public MemJson(String name, String file, int mem_size1,int mem_size2  ) throws IOException {
        _name= name;
        _file = file;
        _mem = new byte[mem_size1][mem_size2];

        load();


    }

    public void load() throws IOException {
        File f = new File(_file);
        FileInputStream fis = new FileInputStream(f);

        for (int i = 0; i < _mem.length; i++) {
            long length = fis.read(_mem[i]);
            if (length <= 0 || length < _mem[0].length - 1) break;
        }

        fis.close();

        System.out.printf("\n%s:%d\n",_file, indexLines());
    }

    private long indexLines() {
        int count = 0, count1 = 0, count2 = 0;
        int length = _mem[0].length;

        boolean EOF = false;
        for (int i = 0; i < _mem.length; i++) {
            for (int j = 0; j < _mem[i].length; j++) {
                if (_mem[i][j] == '\n') // 10
                {
                    _index_cache[count][0] = i;
                    _index_cache[count][1] = j;
                    count++;
                    count1++;
                }
                if (_mem[i][j] == 13) // CR
                {
                    _index_cache[count][0] = i;
                    _index_cache[count][1] = j;
                    count++;
                    count2++;
                }
                if (_mem[i][j] == 0) { //EOF
                    _index_cache[count][0] = i;
                    _index_cache[count][1] = j;
                    count++;
                    EOF = true;
                    break;
                }
            }
            if (EOF) {
                break;
            }
        }
        if (!EOF) {
            _index_cache[count][0] = _mem.length - 1;
            _index_cache[count][1] = _mem[0].length - 1;
            count++;
        }

        long previous_newline_position = 0, next_newline_position = 0;
        int line = 0;
        for (int i = 0; i < count; i++) {
            next_newline_position = (long) _index_cache[i][0] * length + _index_cache[i][1];
            if (next_newline_position - previous_newline_position > 1) {
                line++;
            }
            previous_newline_position = next_newline_position;
        }
        _index = new int[line][4];

        previous_newline_position = -1;
        next_newline_position = 0;
        line = 0;
        for (int i = 0; i < count; i++) {
            next_newline_position = (long) _index_cache[i][0] * length + _index_cache[i][1];
            if (next_newline_position - previous_newline_position > 1) {
                _index[line][0] = (int) ((previous_newline_position + 1) / length);
                _index[line][1] = (int) ((previous_newline_position + 1) % length);
                _index[line][2] = (int) ((next_newline_position - 1) / length);
                _index[line][3] = (int) ((next_newline_position - 1) % length);
                line++;
            }
            previous_newline_position = next_newline_position;
        }
        return line;
    }

}
