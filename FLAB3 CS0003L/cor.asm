%macro show 2
    mov ecx, %1
    mov edx, %2
    call print
%endmacro

%macro ask 3
    show %1, %2
    mov ecx, %3
    mov edx, 128
    call read
%endmacro

%macro value 1
    mov ecx, %1
    call text
%endmacro

section .data
    title db 10, "================ CERTIFICATE OF REGISTRATION ================", 10
    titlelen equ $ - title

    pstatus db "Enrollment Status: "
    statlen equ $ - pstatus

    pnumber db "Student #: "
    numlen equ $ - pnumber

    pname db "Name: "
    namelen equ $ - pname

    pphone db "Phone: "
    phonelen equ $ - pphone

    pzip db "Zip Code: "
    ziplen equ $ - pzip

    pclass db "Classification: "
    classlen equ $ - pclass

    pcollege db "College: "
    collegelen equ $ - pcollege

    pprogram db "Program: "
    programlen equ $ - pprogram

    pyear db "Year Level: "
    yearlen equ $ - pyear

    paddress db "Address: "
    addresslen equ $ - paddress

    askstat db "Enter enrollment status: "
    askstatlen equ $ - askstat

    asknum db "Enter student number: "
    asknumlen equ $ - asknum

    askname db "Enter student name: "
    asknamelen equ $ - askname

    askphone db "Enter phone number: "
    askphonelen equ $ - askphone

    askzip db "Enter zip code: "
    askziplen equ $ - askzip

    askclass db "Enter classification: "
    askclasslen equ $ - askclass

    askcollege db "Enter college: "
    askcollegelen equ $ - askcollege

    askprogram db "Enter program: "
    askprogramlen equ $ - askprogram

    askyear db "Enter year level: "
    askyearlen equ $ - askyear

    askaddress db "Enter address: "
    askaddresslen equ $ - askaddress

    courses db 10, "COURSES", 10
            db "Code       Title                              Section Units Days Time              Room", 10
            db "CCS0021    INFORMATION MANAGEMENT (LEC)      TS21      2    F   07:00-09:40       ONLINE", 10
            db "CCS0021L   INFORMATION MANAGEMENT (LAB)      TS21      1    T   07:00-09:50       ONLINE", 10
            db "CS0003     COMPUTER SYSTEMS & ARCHITECTURE    TN24      2    F   10:00-12:40       ONLINE", 10
            db "CS0003L    COMPUTER SYSTEMS & ARCHITECTURE LAB  TN24      1    TH  10:00-12:50       E610", 10
            db "CS0070     OBJECT ORIENTED PROGRAMMING        TA21      2    T   10:00-12:40       ONLINE", 10
            db "CS0070L    OBJECT ORIENTED PROGRAMMING-LAB    TA21      1    M   10:00-12:50       E609", 10
            db "GED0040    APPLIED STATISTICS FOR CS          M401      3    F/T 15:00-16:50       ONLINE", 10
            db "TOTAL UNITS: 12", 10
    courselen equ $ - courses

    fees db 10, "ASSESSMENT", 10
    feeslen equ $ - fees

    totalname db "Total Units: "
    totallen equ $ - totalname

    tuition db "Tuition Fee: "
    tuitionlen equ $ - tuition

    miscname db "Miscellaneous Fee: "
    misclen equ $ - miscname

    idname db "ID Validation Fee: "
    idlen equ $ - idname

    labone db "Laboratory Fee (CCS0021): "
    labonelen equ $ - labone

    labtwo db "Laboratory Fee (CS0003): "
    labtwolen equ $ - labtwo

    labthree db "Laboratory Fee (CS0070): "
    labthreelen equ $ - labthree

    assessment db "Total Assessment: "
    assesslen equ $ - assessment

    decs db ".00", 10
    decslen equ $ - decs

    unit1 dd 2
    unit2 dd 1
    unit3 dd 2
    unit4 dd 1
    unit5 dd 2
    unit6 dd 1
    unit7 dd 3

    rate dd 2108
    misc dd 12305
    idfee dd 80
    labfee dd 4008
    labs dd 3

    newline db 10
    newlinelen equ $ - newline

section .bss
    stat resb 128
    stud resb 128
    full resb 128
    fone resb 128
    code resb 128
    kind resb 128
    camp resb 128
    prog resb 128
    level resb 128
    place resb 128

    units resd 1
    fee resd 1
    total resd 1
    nums resb 16

section .text
    global _start

_start:
    ask askstat, askstatlen, stat
    ask asknum, asknumlen, stud
    ask askname, asknamelen, full
    ask askphone, askphonelen, fone
    ask askzip, askziplen, code
    ask askclass, askclasslen, kind
    ask askcollege, askcollegelen, camp
    ask askprogram, askprogramlen, prog
    ask askyear, askyearlen, level
    ask askaddress, askaddresslen, place

    show title, titlelen

    show pstatus, statlen
    value stat
    show newline, newlinelen

    show pnumber, numlen
    value stud
    show newline, newlinelen

    show pname, namelen
    value full
    show newline, newlinelen

    show pphone, phonelen
    value fone

    show pzip, ziplen
    value code
    show newline, newlinelen

    show pclass, classlen
    value kind
    show newline, newlinelen

    show pcollege, collegelen
    value camp

    show pprogram, programlen
    value prog
    show newline, newlinelen

    show pyear, yearlen
    value level
    show newline, newlinelen

    show paddress, addresslen
    value place
    show newline, newlinelen

    show courses, courselen
    call compute

    show fees, feeslen

    show totalname, totallen
    mov eax, [units]
    call number
    show newline, newlinelen

    show tuition, tuitionlen
    mov eax, [fee]
    call number
    show decs, decslen

    show miscname, misclen
    mov eax, [misc]
    call number
    show decs, decslen

    show idname, idlen
    mov eax, [idfee]
    call number
    show decs, decslen

    show labone, labonelen
    mov eax, [labfee]
    call number
    show decs, decslen

    show labtwo, labtwolen
    mov eax, [labfee]
    call number
    show decs, decslen

    show labthree, labthreelen
    mov eax, [labfee]
    call number
    show decs, decslen

    show assessment, assesslen
    mov eax, [total]
    call number
    show decs, decslen

    mov eax, 1
    xor ebx, ebx
    int 0x80

print:
    push ebx

    mov eax, 4
    mov ebx, 1
    int 0x80

    pop ebx
    ret

compute:
    push eax
    push ebx
    push ecx

    mov eax, [unit1]
    add eax, [unit2]
    add eax, [unit3]
    add eax, [unit4]
    add eax, [unit5]
    add eax, [unit6]
    add eax, [unit7]
    mov [units], eax

    mov ebx, [rate]
    imul ebx
    mov [fee], eax

    mov eax, [labfee]
    imul dword [labs]
    add eax, [fee]
    add eax, [misc]
    add eax, [idfee]
    mov [total], eax

    pop ecx
    pop ebx
    pop eax
    ret

number:
    push eax
    push ebx
    push edx
    push esi
    push edi

    mov edi, nums + 15

    cmp eax, 0
    jne .digits

    dec edi
    mov byte [edi], '0'
    jmp .show

.digits:
    xor edx, edx
    mov ebx, 10
    div ebx

    add dl, '0'
    dec edi
    mov [edi], dl

    cmp eax, 0
    jne .digits

.show:
    mov ecx, edi
    mov edx, nums + 15
    sub edx, edi
    call print

    pop edi
    pop esi
    pop edx
    pop ebx
    pop eax
    ret

text:
    push eax
    push edx
    push esi

    mov esi, ecx
    xor edx, edx

.count:
    cmp byte [esi + edx], 0
    je .write

    inc edx
    jmp .count

.write:
    call print

    pop esi
    pop edx
    pop eax
    ret

read:
    push eax
    push ebx
    push esi

    mov eax, 3
    xor ebx, ebx
    int 0x80

    mov esi, ecx
    mov ecx, eax

    cmp ecx, 0
    jle .done

.scan:
    cmp byte [esi], 10
    je .stop

    inc esi
    loop .scan
    jmp .done

.stop:
    mov byte [esi], 0

.done:
    mov byte [esi], 0

    pop esi
    pop ebx
    pop eax
    ret