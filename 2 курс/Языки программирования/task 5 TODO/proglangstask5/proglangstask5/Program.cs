using System.Runtime.InteropServices;
using System.Text;

class Program
{
    readonly static string info = """
                                  Commands:
                                  expr <expression>         Set expression
                                  set <variable> <value>    Set variable
                                  do                        Evaluate
                                  info                      Commands info
                                  exit                      Quit
                                  """;

    static Dictionary<string, string> variables = new Dictionary<string, string>();
    static string expr;

    static string[] tokens;

    static List<string> operators = new List<string> { "+", "-", "*", "/" };

    static Dictionary<string, int> operatorsWithPriority = new Dictionary<string, int>
    {
        { "+", 1 },
        { "-", 1 },
        { "*", 2 },
        { "/", 2 }
    };


    [DllImport("kernel32.dll")]
    static extern IntPtr LoadLibrary(string libFileName);

    [DllImport("kernel32.dll")]
    static extern IntPtr GetProcAddress(IntPtr libAddr, string functionName);

    [DllImport("kernel32.dll")]
    static extern bool FreeLibrary(IntPtr libAddr);

    delegate void FuncDelegate();

    static void Main(string[] args)
    {
        Console.WriteLine(info);
        Console.WriteLine("dir: " + Directory.GetCurrentDirectory());

        while (true)
        {
            Console.Write("> ");
            string input = Console.ReadLine();

            if (input.StartsWith("expr "))
            {
                string[] line = input.Split(" ", 2);
                expr = line[1];
                tokens = new string[expr.Length];
                for (var i = 0; i < expr.Length; i++)
                {
                    tokens[i] = expr[i].ToString();
                }
            }
            else if (input.StartsWith("set ") && !String.IsNullOrEmpty(expr))
            {
                string s = input.Substring(4, input.Length - 4);


                String[] varVal = s.Split(" ", 2);

                string variable = varVal[0];
                string value = varVal[1];
                variables[variable] = value;
            }
            else if (input.Equals("do") && !String.IsNullOrEmpty(expr))
            {
                Console.WriteLine($"infix: {expr}");
                string result = ShuntingYard(tokens);
                Console.WriteLine($"rpn: {result}");

                HandleRpnExpr(result);
            }
            else if (input.Equals("exit"))
            {
                break;
            }
            else if (input.Equals("info"))
            {
                Console.WriteLine(info);
            }
            else
            {
                Console.WriteLine("Enter correct data!");
            }
        }
    }


    static void HandleRpnExpr(string expr)
    {
        Stack<string> stack = new Stack<string>();
        string funcArgs = "";
        StringBuilder funcBody = new StringBuilder();
        for (var i = 0; i < expr.Length; i++)
        {
            string token = expr[i].ToString();
            if (variables.ContainsKey(token))
            {
                funcBody.AppendLine($"    long long {token} = {variables[token]};");
            }

            if (token == " ") continue;
            if (operators.Contains(token)) // operator 
            {
                string rightVar = stack.Pop();
                string leftVar = stack.Pop();
                string rightVal;
                string leftVal;

                if (variables.ContainsKey(rightVar)) rightVal = variables[rightVar];
                else rightVal = rightVar;
                if (variables.ContainsKey(leftVar)) leftVal = variables[leftVar];
                else leftVal = leftVar;


                string varName = $"expr{i}";
                funcBody.AppendLine($"    long long {varName} = {leftVal} {token} {rightVal};");
                funcBody.AppendLine(
                    $"    printf(\"%d %s %d = %d\\n\", {leftVar}, \"{token}\", {rightVar}, {varName});");
                stack.Push(varName);
            }
            else // operand
            {
                stack.Push(token);
            }
        }

        RunCFile(funcArgs, funcBody.ToString());
    }
    
    private static void RunCFile(string funcArgs, string funcBody)
    {
        string path = "mylib.c";
        File.WriteAllText(path, "#include <stdio.h>\n\nvoid func(" + funcArgs + ") {\n" + funcBody + "}");
        string currentDir = Directory.GetCurrentDirectory();

        string libC = "mylib.c";
        string libOBJ = "mylib.obj";
        string libDLL = "mylib.dll";

        string myLibC = Path.Combine(currentDir, libC);
        string myLibOBJ = Path.Combine(currentDir, libOBJ);
        string myLibDLL = Path.Combine(currentDir, libDLL);


        if (File.Exists(myLibOBJ)) File.Delete(myLibOBJ);
        if (File.Exists(myLibDLL)) File.Delete(myLibDLL);

        string gccCommand =
            $"/c gcc -c -fPIC \"{myLibC}\" -o \"{myLibOBJ}\" && gcc -shared -o \"{myLibDLL}\" \"{myLibOBJ}\"";
        var process = System.Diagnostics.Process.Start("cmd.exe", gccCommand);
        process.WaitForExit();

        IntPtr mylib = IntPtr.Zero;
        IntPtr funcPtr = IntPtr.Zero;

        mylib = LoadLibrary(libDLL);

        if (mylib == IntPtr.Zero)
        {
            Console.WriteLine("mylib не загрузилась!");
        }

        funcPtr = GetProcAddress(mylib, "func");

        if (funcPtr == IntPtr.Zero)
        {
            Console.WriteLine("funcPtr == null!");
        }

        FuncDelegate func = (FuncDelegate)Marshal.GetDelegateForFunctionPointer(funcPtr, typeof(FuncDelegate));

        func();

        FreeLibrary(mylib);
    }

    static string ShuntingYard(string[] tokens)
    {
        Stack<string> stack = new Stack<string>();
        List<string> rpn = new List<string>();

        for (var i = 0; i < tokens.Length; i++)
        {
            string token = tokens[i];

            if (token == " ") continue;
            if (operators.Contains(token)) // operator
            {
                while (stack.Count > 0 && operators.Contains(stack.Peek()) &&
                       operatorsWithPriority[stack.Peek()] >= operatorsWithPriority[token])
                {
                    rpn.Add(stack.Pop());
                }


                stack.Push(token);
            }
            else if (token.Equals("("))
            {
                stack.Push(token);
            }
            else if (token.Equals(")"))
            {
                while (!stack.Peek().Equals("("))
                {
                    rpn.Add(stack.Pop());
                }

                stack.Pop();
            }
            else // operand
            {
                rpn.Add(token);
            }
        }

        while (stack.Count > 0)
        {
            rpn.Add(stack.Pop());
        }

        return String.Join(" ", rpn);
    }
}
//test expr:
//expr a+b
//expr (a+b)
//expr a+b*(c-d)
//expr 3+2*(6-4)
//expr a*b/c+d*e-f/g