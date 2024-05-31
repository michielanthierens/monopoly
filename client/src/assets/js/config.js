"use strict";

const _config = {
    prefix: 'group08',
    groupnumber: '08',
    pawns: ["blaze", "cow", "creeper", "enderman", "guardian", "pig", "sheep", "skeleton", "slime", "spider", "steve", "villager", "wither", "zombie"],
    playersAndPawns: [],
    errorHandlerSelector: '.errormessages p',
    getAPIUrl: function () { return `https://project-i.ti.howest.be/monopoly-${this.groupnumber}/api`; }
};

