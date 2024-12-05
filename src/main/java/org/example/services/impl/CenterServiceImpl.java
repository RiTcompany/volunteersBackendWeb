package org.example.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.entities.BotUser;
import org.example.entities.Center;
import org.example.entities.Role;
import org.example.entities.Volunteer;
import org.example.enums.ERole;
import org.example.exceptions.CenterNotFoundException;
import org.example.exceptions.PermissionDeniedException;
import org.example.mapper.CenterMapper;
import org.example.pojo.dto.card.CenterCardDto;
import org.example.pojo.dto.create.CenterCreateDto;
import org.example.pojo.dto.table.CenterTableDto;
import org.example.pojo.dto.update.CenterUpdateDto;
import org.example.repositories.CenterRepository;
import org.example.repositories.UserRepository;
import org.example.repositories.VolunteerRepository;
import org.example.services.CenterService;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CenterServiceImpl implements CenterService {
    private final CenterRepository centerRepository;
    private final CenterMapper centerMapper;
    private final UserRepository userRepository;
    private final VolunteerRepository volunteerRepository;

    @Override
    public List<CenterTableDto> getCenterList() {
        /*BotUser botUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found by username: " + SecurityContextHolder.getContext().getAuthentication().getName()));
        if (botUser.getRoleList().stream()
                .map(Role::getRoleName)
                .noneMatch(role -> role == ERole.ROLE_REGIONAL_HEAD || role == ERole.ROLE_REGIONAL_TEAM_HEAD)) {
            throw new PermissionDeniedException();
        }*/
        return centerRepository.findAll().stream().map(centerMapper::centerDto).toList();
    }

    @Override
    public long addCenter(CenterCreateDto centerCreateDto) {
        /*BotUser botUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found by username: " + SecurityContextHolder.getContext().getAuthentication().getName()));
        if (botUser.getRoleList().stream()
                .map(Role::getRoleName)
                .noneMatch(role -> role == ERole.ROLE_REGIONAL_HEAD || role == ERole.ROLE_REGIONAL_TEAM_HEAD)) {
            throw new PermissionDeniedException();
        }*/
        return centerRepository.saveAndFlush(centerMapper.center(centerCreateDto)).getId();
    }

    @Override
    public long deleteCenter(Long id) {
        /*BotUser botUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found by username: " + SecurityContextHolder.getContext().getAuthentication().getName()));
        if (botUser.getRoleList().stream()
                .map(Role::getRoleName)
                .noneMatch(role -> role == ERole.ROLE_REGIONAL_HEAD || role == ERole.ROLE_REGIONAL_TEAM_HEAD)) {
            throw new PermissionDeniedException();
        }*/
        centerRepository.deleteById(id);
        return id;
    }

    @Override
    public CenterCardDto getCenterCard(Long id) {
        /*Volunteer volunteer = volunteerRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found by username: " + SecurityContextHolder.getContext().getAuthentication().getName()));
        BotUser botUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found by username: " + SecurityContextHolder.getContext().getAuthentication().getName()));
        if (botUser.getRoleList().stream()
                .map(Role::getRoleName)
                .noneMatch(role -> role == ERole.ROLE_REGIONAL_HEAD || role == ERole.ROLE_REGIONAL_TEAM_HEAD) && !volunteer.getCenter().getId().equals(id)) {
            throw new PermissionDeniedException();
        }*/
        return centerRepository.findById(id).map(centerMapper::centerCardDto)
                .orElseThrow(() -> new CenterNotFoundException(id.toString()));
    }

    @Override
    public void update(List<CenterUpdateDto> dtoList) {
       /* BotUser botUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found by username: " + SecurityContextHolder.getContext().getAuthentication().getName()));
        if (botUser.getRoleList().stream()
                .map(Role::getRoleName)
                .noneMatch(role -> role == ERole.ROLE_REGIONAL_HEAD || role == ERole.ROLE_REGIONAL_TEAM_HEAD)) {
            throw new PermissionDeniedException();
        }*/
        dtoList.forEach(dto -> {
                    Center center = centerRepository.findById(dto.getId()).orElseThrow(() -> new CenterNotFoundException(dto.getId().toString()));
                    centerRepository.saveAndFlush(centerMapper.center(center, dto));
                }
        );
    }
}
