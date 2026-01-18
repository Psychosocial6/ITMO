using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Reflection.Emit;
using System.Text;
using System.Text.RegularExpressions;

namespace ExpressionEvaluator
{
    public delegate long ExpressionDelegate(Dictionary<string, long> variables);

    class Program
    {
        static Dictionary<string, long> variables = new Dictionary<string, long>();
        static ExpressionDelegate currentMethod = null;
        static string currentExprText = "";

        static void Main(string[] args)
        {
            while (true)
            {
                Console.Write("> ");
                string input = Console.ReadLine();
                if (string.IsNullOrWhiteSpace(input)) continue;

                string[] parts = input.Split(new[] { ' ' }, 2);
                string command = parts[0].ToLower();
                string argument = parts.Length > 1 ? parts[1].Trim() : "";

                try
                {
                    switch (command)
                    {
                        case "expr":
                            currentExprText = argument;
                            currentMethod = CompileExpression(argument);
                            break;
                        case "set":
                            SetVariable(argument);
                            break;
                        case "do":
                            if (currentMethod == null) Console.WriteLine("Error: No expression set.");
                            else currentMethod(variables);
                            break;
                        case "exit":
                            return;
                        default:
                            Console.WriteLine("Unknown command.");
                            break;
                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine($"Error: {ex.Message}");
                }
            }
        }

        static void SetVariable(string arg)
        {
            var parts = arg.Split(new[] { ' ' }, StringSplitOptions.RemoveEmptyEntries);
            if (parts.Length != 2) throw new Exception("Usage: set <var> <value>");
            variables[parts[0]] = long.Parse(parts[1]);
        }

        static List<Token> ShuntingYard(string expression)
        {
            var output = new List<Token>();
            var stack = new Stack<Token>();
            var tokens = Tokenize(expression);

            foreach (var token in tokens)
            {
                if (token.Type == TokenType.Number || token.Type == TokenType.Variable)
                {
                    output.Add(token);
                }
                else if (token.Type == TokenType.OpenParenthesis)
                {
                    stack.Push(token);
                }
                else if (token.Type == TokenType.CloseParenthesis)
                {
                    while (stack.Peek().Type != TokenType.OpenParenthesis)
                        output.Add(stack.Pop());
                    stack.Pop();
                }
                else
                {
                    while (stack.Count > 0 && stack.Peek().Type != TokenType.OpenParenthesis &&
                           GetPrecedence(stack.Peek().Value) >= GetPrecedence(token.Value))
                    {
                        output.Add(stack.Pop());
                    }
                    stack.Push(token);
                }
            }
            while (stack.Count > 0) output.Add(stack.Pop());
            return output;
        }

        static int GetPrecedence(string op) => (op == "+" || op == "-") ? 1 : 2;

        static IEnumerable<Token> Tokenize(string expr)
        {
            var pattern = @"\d+|[a-zA-Z_]\w*|[\+\-\*\/\(\)]";
            foreach (Match m in Regex.Matches(expr, pattern))
            {
                string val = m.Value;
                if (char.IsDigit(val[0])) yield return new Token(TokenType.Number, val);
                else if (char.IsLetter(val[0])) yield return new Token(TokenType.Variable, val);
                else if (val == "(") yield return new Token(TokenType.OpenParenthesis, val);
                else if (val == ")") yield return new Token(TokenType.CloseParenthesis, val);
                else yield return new Token(TokenType.Operator, val);
            }
        }

        static ExpressionDelegate CompileExpression(string expr)
        {
            var rpn = ShuntingYard(expr);
            var method = new DynamicMethod("Eval", typeof(long), new[] { typeof(Dictionary<string, long>) });
            var il = method.GetILGenerator();

            var exprStack = new Stack<string>();

            foreach (var token in rpn)
            {
                if (token.Type == TokenType.Number)
                {
                    long val = long.Parse(token.Value);
                    il.Emit(OpCodes.Ldc_I8, val);
                    exprStack.Push(token.Value);
                }
                else if (token.Type == TokenType.Variable)
                {
                    il.Emit(OpCodes.Ldarg_0);
                    il.Emit(OpCodes.Ldstr, token.Value);
                    il.Emit(OpCodes.Callvirt, typeof(Dictionary<string, long>).GetMethod("get_Item"));
                    exprStack.Push(token.Value);
                }
                else if (token.Type == TokenType.Operator)
                {
                    string rightExpr = exprStack.Pop();
                    string leftExpr = exprStack.Pop();

                    string currentSubExpr;
                    if (token.Value == "*" || token.Value == "/")
                        currentSubExpr = FormatBinary(leftExpr, rightExpr, token.Value);
                    else
                        currentSubExpr = $"{leftExpr}{token.Value}{rightExpr}";

                    switch (token.Value)
                    {
                        case "+": il.Emit(OpCodes.Add); break;
                        case "-": il.Emit(OpCodes.Sub); break;
                        case "*": il.Emit(OpCodes.Mul); break;
                        case "/": il.Emit(OpCodes.Div); break;
                    }

                    il.Emit(OpCodes.Dup);
                    il.Emit(OpCodes.Ldstr, $"{currentSubExpr} = ");
                    il.Emit(OpCodes.Call, typeof(Console).GetMethod("Write", new[] { typeof(string) }));
                    il.Emit(OpCodes.Call, typeof(Console).GetMethod("WriteLine", new[] { typeof(long) }));

                    exprStack.Push(token.Value == "*" || token.Value == "/" ? currentSubExpr : $"({currentSubExpr})");
                }
            }

            il.Emit(OpCodes.Ret);
            return (ExpressionDelegate)method.CreateDelegate(typeof(ExpressionDelegate));
        }

        static string FormatBinary(string left, string right, string op)
        {
            return $"{left}{op}{right}";
        }
    }

    enum TokenType { Number, Variable, Operator, OpenParenthesis, CloseParenthesis }
    class Token
    {
        public TokenType Type;
        public string Value;
        public Token(TokenType t, string v) { Type = t; Value = v; }
    }
}