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

    // static string funcBody = "int stack[1000];\nint sp =0;";
    static List<string> operators = new List<string> { "+", "-", "*", "/" };

    static Dictionary<string, int> operatorsWithPriority = new Dictionary<string, int>
    {
        { "+", 1 },
        { "-", 1 },
        { "*", 2 },
        { "/", 2 }
    };

    static void Main(string[] args)
    {
        // Console.WriteLine(info);

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
                // for (var i = 5; i - 5 < input.Length; i++)
                // {
                //     Console.WriteLine(input[i].ToString());
                //     tokens.Append(input[i].ToString());
                // }
                // expr = String.Join("", tokens);
            }
            else if (input.StartsWith("set ") && !String.IsNullOrEmpty(expr))
            {
                // char[] varValChars = new char[] { };
                // for (var i = 4; i < input.Length; i++)
                // {
                //     varValChars[i] = input[i];
                // }

                string s = input.Substring(4, input.Length - 4);


                // string varValString = new String(varValChars);
                String[] varVal = s.Split(" ", 2);

                string variable = varVal[0];
                string value = varVal[1];
                variables[variable] = value;
            }
            else if (input.Equals("do") && !String.IsNullOrEmpty(expr))
            {
                Console.WriteLine($"before: {expr}");
                string result = ShuntingYard(tokens);
                Console.WriteLine($"after: {result}");

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


    static void HandleRpnExpr(string expr) //, string funcBody
    {
        Stack<string> stack = new Stack<string>();
        string funcArgs = "";
        StringBuilder funcBody = new StringBuilder();
        for (var i = 0; i < expr.Length; i++)
        {
            string token = expr[i].ToString();
            if (variables.ContainsKey(token))
            {
                funcArgs += $"int {token}, ";
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
                funcBody.AppendLine($"    int {varName} = {leftVal} {token} {rightVal};");
                funcBody.AppendLine($"    printf(\"%d %s %d = %d\n\", {leftVar}, \"{token}\", {rightVar}, {varName});");
                stack.Push(varName);
            }
            else // operand
            {
                stack.Push(token);
            }

            if (i == expr.Length - 1) funcArgs = funcArgs.Substring(0, funcArgs.Length - 2); // обрезка ", "
        }

        string path = "mylib.c";
        File.WriteAllText(path, "#include <stdio.h>\n\nvoid func(" + funcArgs + ") {\n" + funcBody + "}");
        // System.Diagnostics.Process.Start(""); gcc -fPIC -shared mylib.c -o mylib.dll
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
                //expr a+b*(c-d)

                //expr 3+2*(6-4)
                //expr (a+b)
                while (stack.Count > 0 && operators.Contains(stack.Peek()) &&
                       operatorsWithPriority[stack.Peek()] >= operatorsWithPriority[token])
                {
                    rpn.Add(stack.Pop());
                }
                // Console.Write($"string expr{i} = {String.Join("", rpn)};\n");


                stack.Push(token);
            }
            else if (token.Equals("(")) // (
            {
                stack.Push(token);
            }
            else if (token.Equals(")")) // ) 
            {
                while (!stack.Peek().Equals("("))
                    // Console.WriteLine(stack.First());
                {
                    rpn.Add(stack.Pop());
                    // Console.WriteLine(stack.Pop());
                }

                // Console.Write($"string expr{i} = {String.Join("", rpn)};\n");
                stack.Pop();
            }
            else // operand
            {
                rpn.Add(token);
                // Console.Write($"string expr{i} = {String.Join("", rpn)};\n");
            }
        }

        while (stack.Count > 0)
        {
            rpn.Add(stack.Pop());
            // Console.Write($"string expr{tokens.Length} = {String.Join("", rpn)};\n");
        }

        return String.Join(" ", rpn);
    }
}
//expr a+b
//expr a*b/c+d*e-f/g