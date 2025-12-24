

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
    static string funcBody = "";
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
                char[] varValChars = new char[] { };
                for (var i = 4; i < input.Length; i++)
                {
                    varValChars[i] = input[i];
                }

                string varValString = new String(varValChars);
                String[] varVal = varValString.Split(" ", 2);

                string variable = varVal[0];
                string value = varVal[1];
                variables[variable] = value;
            }
            else if (input.Equals("do") && !String.IsNullOrEmpty(expr))
            {
                Console.WriteLine($"before: {expr}");
                string result = ShuntingYard(tokens);
                Console.WriteLine($"after: {result}");
                
                // HandleExpr(result);
                
              
                
                
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


    static void HandleExpr(string expr, string funcBody)
    {
        string funcArgs = "";
        // string funcBody = "";
        for (var i = 0; i < expr.Length; i++)
        {
            string key = expr[i].ToString();
            if (variables.ContainsKey(key))
            {
                funcArgs += $"int {key}, ";
                // funcBody += $"string expr{i} = "
            }

            if (i == expr.Length - 1) funcArgs = funcArgs.Substring(0, funcArgs.Length - 2); // обрезка ", "
        }

        string path = "proglangstask5/mylib.c";
        File.Create(path);
        File.WriteAllText(path,"#include <stdio.h>\n void func(" + funcArgs + ") {\n" + funcBody + "\n}");
        
    }

    
    static string ShuntingYard(string[] tokens)
    {
        Dictionary<string, int> operators = new Dictionary<string, int>
        {
            { "+", 1 },
            { "-", 1 },
            { "*", 2 },
            { "/", 2 }
        };

        Stack<string> stack = new Stack<string>();
        List<string> rpn = new List<string>();

        for (var i = 0; i < tokens.Length; i++)
        {
            string token = tokens[i];
            List<string> opps = new List<string> { "+", "-", "*", "/" };
            if (opps.Contains(token)) // operator
            {
                //expr a+b*(c-d)
                
                //expr 3+2*(6-4)
                //expr (a+b)
                while (stack.Count > 0 && opps.Contains(stack.Peek()) && operators[stack.Peek()] >= operators[token])
                {
                    rpn.Add(stack.Pop());
                }
                
                
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
        return String.Join("", rpn);
    }
}
//expr (a+b)