package args;

import java.util.HashMap;
import java.util.Map;

public class Args {

    Map<String,String> flagMap = new HashMap();
    Map<String,Object> resultMap = new HashMap();


    /**
     * Description linux参数处理器
     *
     * @param flags 对应标志位key-value, bool, int ,string, int[], string[]
     * @param command 字符串，需要分隔处理
     * @author hongyuncao
     * @date 2019/10/15 15:30
     */
    public Args(String flags, String command) {
        //获取参数Map
        getMapFromFlags(flags);

        //处理命令行
        String[] commandArray = command.split("-");
        for (int i = 1; i<commandArray.length; i++){
            parseCommand(commandArray[i]);
        }
    }

    private void parseCommand(String commandArray) {
        //获取对应参数
        char command = commandArray.charAt(0);
        String param = commandArray.substring(1).trim();

        //将获取到的值设置到map中
        if (command >= '0' && command <='9'){
            resultMap.put("d", 0-(Integer.valueOf(command)-'0'));
            return;
        }
        if (command == 'd'){
            if (param.equals("")){
                return;
            }
            else{
                resultMap.put(String.valueOf(command), Integer.valueOf(param));
                return;
            }
        }
        resultMap.put(String.valueOf(command), param);
    }

    /**
     * Description 将字符串分割处理拿到参数Map
     *
     * @param flags 字符串入参
     * @author hongyuncao
     * @date 2019/10/15 15:41
     */
    private void getMapFromFlags(String flags) {
        String[] splitMap = flags.split(",");
        for (int i = 0; i<splitMap.length; i++){
            String[] keyValue = splitMap[i].split(":");
            flagMap.put(keyValue[0], keyValue[1]);
        }
    }

    public Object getValue(String flag) {
        String key = flagMap.get(flag);
        Object value = resultMap.get(flag);

        //处理BOOL类型
        if (key.equals("bool")){
            if (value.equals("")){
                return false;
            }else {
                return true;
            }
        }
        //处理空字符串
        if (key.equals("string")){
            if (value.equals("")){
                return null;
            }
        }
        return value;
    }

}
