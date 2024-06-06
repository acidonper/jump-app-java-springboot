curl -XPOST -H "Content-Type: application/json"  -H "X-B3-TraceId: hola" -H "X-B3-ParentSpanId: adios" -H "X-B3-SpanId: hello"  -v -d '{
    "message": "Hello",
    "last_path": "/jump",
    "jump_path": "/jump",
    "jumps": [
        "http://localhost:8443",
        "http://localhost:8443"
    ]
}' 'localhost:8443/jump'

# curl -XPOST -H "Content-Type: application/json" -v -d '{
#     "message": "Hello",
#     "last_path": "/jump",
#     "jump_path": "/jump",
#     "jumps": []
# }' 'localhost:8443/jump'

# curl localhost:8443/jump -H "X-B3-TraceId: hola" -H "X-B3-ParentSpanId: adios" -H "X-B3-SpanId: hello"  -v